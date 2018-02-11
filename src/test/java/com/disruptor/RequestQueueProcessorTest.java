package com.disruptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;

/**
 * @author by cairongfu.crf
 * @since on 2018/2/11 11:41.
 */
public class RequestQueueProcessorTest {
    static final RequestQueueProcessor processor;

    static {
        processor = new RequestQueueProcessor(30000, 10, "TestProcessor");
    }

    public static void main(String[] args) {
        //test1();
        //test2();
        test3();
        processor.shutdown();
    }

    private static void test1() {
        printStat();
        for (int i = 0; i < 10000; i++) {
            final int num = i;
            boolean result = processor.process(
                () -> System.out.println("single work-" + num + " handled by " + Thread.currentThread().getName()));
            System.out.println("result:" + result);
        }
        printStat();
    }

    private static void test2() {
        printStat();
        List<Runnable> task = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            final int num = i;
            task.add(() -> System.out.println("batch work-" + num + " handled by " + Thread.currentThread().getName()));
        }
        boolean result = processor.processBatch(task);
        System.out.println("batch result:" + result);
        printStat();
    }

    private static void test3() {
        printStat();
        for (int i = 0; i < 40000; i++) {
            final int num = i;
            boolean result = processor.process(
                () -> {
                    System.out.println("single work-" + num + " handled by " + Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            System.out.println("result:" + result);
        }
        printStat();
    }

    private static void printStat() {
        System.out.println("maxQueueSz=" + processor.getMaxQueueSz());
        System.out.println("availableCapacity=" + processor.getAvailableCapacity());
        System.out.println("pendingRequests=" + processor.getPendingRequests());
        System.out.println("droppedRequests=" + processor.getDroppedRequests());
    }
}
