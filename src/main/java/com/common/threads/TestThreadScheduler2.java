package com.common.threads;

import java.util.concurrent.TimeUnit;

/**
 * 多线程顺序执行
 *
 * @author cairongfu
 */
public class TestThreadScheduler2 {

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": t1 run...");
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ": t2 run...");
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ": t3 run...");
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
