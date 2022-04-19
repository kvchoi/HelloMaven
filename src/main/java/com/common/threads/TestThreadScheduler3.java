package com.common.threads;

/**
 * 多线程顺序执行（Wait/Notify）
 *
 * @author cairongfu
 */
public class TestThreadScheduler3 {

    public static void main(String[] args) throws Exception {
        int loop  = 100;
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (a) {
                try {
                    a.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < loop; i++) {
                System.out.println(Thread.currentThread().getName() + ": t1 run...@" + i);
                synchronized (b) {
                    b.notify();
                }
                if(i + 1 < loop) {
                    synchronized (a) {
                        try {
                            a.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (b) {
                try {
                    b.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < loop; i++) {
                System.out.println(Thread.currentThread().getName() + ": t2 run...@" + i);
                synchronized (c) {
                    c.notify();
                }
                if(i + 1 < loop) {
                    synchronized (b) {
                        try {
                            b.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Thread t3 = new Thread(() -> {
            synchronized (c) {
                try {
                    c.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < loop; i++) {
                System.out.println(Thread.currentThread().getName() + ": t3 run...@" + i);
                synchronized (a) {
                    a.notify();
                }
                if(i + 1 < loop) {
                    synchronized (c) {
                        try {
                            c.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        // 开工
        synchronized (a) {
            a.notifyAll();
        }
    }
}
