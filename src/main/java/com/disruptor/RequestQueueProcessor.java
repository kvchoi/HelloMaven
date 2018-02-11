package com.disruptor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * request queue processor
 *
 * @author cairongfu.crf
 */
public class RequestQueueProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestQueueProcessor.class);

    /**
     * Thread factory that duplicates {@link java.util.concurrent.Executors.DefaultThreadFactory} and allows the
     * thread name to be set.
     */
    private static class NameableThreadFactory implements ThreadFactory {

        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        public NameableThreadFactory(final String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = name + "-worker-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }

    }

    private static class QueueProcessorExceptionHandler implements ExceptionHandler {

        private AtomicLong dropCounter;
        private String name;

        public QueueProcessorExceptionHandler(AtomicLong dropCounter, String name) {
            this.dropCounter = dropCounter;
            this.name = name;
        }

        @Override
        public void handleEventException(Throwable ex, long sequence, Object event) {

            try {
                dropCounter.incrementAndGet();
                LOGGER.error("RequestQueueProcessor : " + name
                    + " Caught Exception - sequence = " + sequence
                    + " Exception = " + ex.getLocalizedMessage());
            } catch (Throwable newEx) {
                // should never through exception on the exception handler.
                // ignore
            }

        }

        @Override
        public void handleOnStartException(Throwable ex) {

            LOGGER.error(
                "RequestQueueProcessor" + name
                    + " On Start exception - Exception = "
                    + ex.getLocalizedMessage());

        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
            LOGGER.error(
                "RequestQueueProcessor" + name
                    + " On Shutdown exception - Exception = "
                    + ex.getLocalizedMessage());

        }

    }

    private static class RequestHolder<T> {
        private T item;

        public T remove() {
            T t = item;
            item = null;
            return t;
        }

        public void put(T event) {
            this.item = event;
        }
    }

    private static class RequestHolderFactory<T> implements EventFactory<RequestHolder<T>> {

        @Override
        public RequestHolder<T> newInstance() {
            return new RequestHolder<T>();
        }

    }

    private static class QueueProcessorWorkHandler implements WorkHandler<RequestHolder<Runnable>> {

        public QueueProcessorWorkHandler() {
        }

        @Override
        public void onEvent(RequestHolder<Runnable> event) throws Exception {
            Runnable req = event.remove();
            req.run();
        }
    }

    // worker pool related items
    private RingBuffer<RequestHolder<Runnable>> ringBuffer;
    private WorkerPool<RequestHolder<Runnable>> workerPool;
    private AtomicLong dropCounter = new AtomicLong(0);
    private int maxQueueSize = 4 * 1024;
    private ExecutorService executor;
    private String name;

    /**
     * create processor with default name
     */
    public RequestQueueProcessor() {
        this(null);
    }

    /**
     * create processor with 32768 queue size and 2 process thread
     *
     * @param name
     */
    public RequestQueueProcessor(String name) {
        this(4 * 1024, 2, name);
    }

    /**
     * create processor with specific queue size and threads size, also the processor name
     *
     * @param maxQueueSize
     * @param numThreads
     * @param name
     */
    public RequestQueueProcessor(int maxQueueSize, int numThreads, String name) {

        this.name = StringUtils.defaultIfEmpty(name, "defaultProcessor");
        this.maxQueueSize = normalizeBufferSize(maxQueueSize);

        this.ringBuffer = RingBuffer.createMultiProducer(
            new RequestHolderFactory<Runnable>(),
            this.maxQueueSize,
            new BlockingWaitStrategy());

        QueueProcessorWorkHandler[] handlers = new QueueProcessorWorkHandler[numThreads];

        for (int i = 0; i < numThreads; i++) {
            handlers[i] = new QueueProcessorWorkHandler();
        }

        this.workerPool = new WorkerPool<>(
            ringBuffer, ringBuffer.newBarrier(),
            new QueueProcessorExceptionHandler(dropCounter, name),
            handlers);

        this.ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        this.executor = Executors.newFixedThreadPool(numThreads, new NameableThreadFactory(name));
        this.workerPool.start(executor);
    }

    /**
     * try to normalize the buffer size to a complex number, if negative number set to 4K default
     *
     * @param bufferSize
     * @return
     */
    private int normalizeBufferSize(int bufferSize) {
        if (bufferSize <= 0) {
            return 4 * 1024;
        }
        int ringBufferSize = 2;
        while (ringBufferSize < bufferSize) {
            ringBufferSize *= 2;
        }
        return ringBufferSize;
    }

    /**
     * Enable publish batch messages to the queue. drop the message when the queue has not enough capacity
     *
     * @param request
     * @return
     */
    public boolean process(Runnable request) {
        long seq;
        try {
            seq = ringBuffer.tryNext();
        } catch (InsufficientCapacityException e1) {
            return false;
        }

        RequestHolder<Runnable> item = this.ringBuffer.get(seq);
        item.put(request);
        this.ringBuffer.publish(seq);
        return true;
    }

    /**
     * @return
     */
    public int getMaxQueueSz() {
        return this.maxQueueSize;
    }

    /**
     * shutdown gently
     */
    public void shutdown() {
        if (this.workerPool.isRunning()) {
            this.workerPool.drainAndHalt();
            this.executor.shutdown();
            LOGGER.warn("RequestQueueProcessor : " + this.name + " is shutting down");
        }

    }

    /**
     * check how many request are pending to be handled
     *
     * @return
     */
    public long getPendingRequests() {
        return this.ringBuffer.getBufferSize() - this.ringBuffer.remainingCapacity();
    }

    /**
     * check is there enough capacity
     *
     * @param requiredCapacity
     * @return
     */
    public boolean hasAvailableCapacity(int requiredCapacity) {
        return this.ringBuffer.hasAvailableCapacity(requiredCapacity);
    }

    /**
     * check how many request be dropped (maybe cause exception when handling)
     *
     * @return
     */
    public long getDroppedRequests() {
        return this.dropCounter.get();
    }

    /**
     * @return
     */
    public long getAvailableCapacity() {
        return this.ringBuffer.remainingCapacity();
    }

    /**
     * Enable publish batch messages to the queue. drop all messages when the queue has not enough capacity
     *
     * @param requests
     * @return
     */
    public boolean processBatch(List<Runnable> requests) {
        final int batchSize = requests.size();
        long seq;

        try {
            seq = this.ringBuffer.tryNext(batchSize);
        } catch (InsufficientCapacityException e1) {
            return false;
        }

        for (int i = 0; i < batchSize; i++) {
            RequestHolder<Runnable> item = this.ringBuffer.get(seq - batchSize + i + 1);
            item.put(requests.get(i));
        }

        if (batchSize > 1) {
            this.ringBuffer.publish(seq - batchSize + 1, seq);
        } else {
            this.ringBuffer.publish(seq);
        }

        return true;
    }

}
