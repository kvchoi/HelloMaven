package com.common.threads;

import java.util.concurrent.*;

/**
 * 多线程顺序执行
 *
 * @author cairongfu
 */
public class TestThreadScheduler {

    public static void main(String[] args) throws Exception {
        Runnable t1 = () -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(Thread.currentThread().getName() + ": t1 run...");
        };

        Runnable t2 = () -> {
            System.out.println(Thread.currentThread().getName() + ": t2 run...");
        };

        Runnable t3 = () -> {
            System.out.println(Thread.currentThread().getName() + ": t3 run...");
        };

        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletableFuture.runAsync(t1, executor).thenRunAsync(t2, executor).thenRunAsync(t3, executor)
                .whenComplete((a, b) -> executor.shutdown()).get();
    }
}
