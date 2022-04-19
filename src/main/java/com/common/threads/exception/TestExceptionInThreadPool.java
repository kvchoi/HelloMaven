package com.common.threads.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class TestExceptionInThreadPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestExceptionInThreadPool.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        threadPoolExecutor.execute(() -> {
                    throw new RuntimeException("execute()发生异常");
                }
        );
        TimeUnit.MILLISECONDS.sleep(200L);

        threadPoolExecutor.submit((Runnable) () -> {
            throw new RuntimeException("submit.run()发生异常");
        });
        TimeUnit.MILLISECONDS.sleep(200L);

        threadPoolExecutor.submit((Callable<String>) () -> {
            throw new RuntimeException("submit.call()发生异常");
        }).get();   //get()的时候会将异常抛出

        threadPoolExecutor.shutdown();
    }

    /**
     * 重写afterExecute方法的线程池工厂
     */
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2,
            4,
            1L,
            TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(1024),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    //单独设置线程异常处理器
                    thread.setUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
                    return thread;
                }
            }
    ) {
        /**
         * 捕获{@code FutureTask<?>}抛出的异常
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (r instanceof FutureTask<?>) {
                try {
                    //get()的时候会将异常内的异常抛出
                    ((FutureTask<?>) r).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    LOGGER.error("[{}], 捕获到线程的异常返回值: [{}]", Thread.currentThread().getName(), e.getMessage());
                }
            }
            //Throwable t永远为null，拿不到异常信息
            //log.error("afterExecute中捕获到异常，", t);
        }
    };

}
