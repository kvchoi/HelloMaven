package com.common.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程顺序执行（Await/Signal）
 *
 * @author cairongfu
 */
public class TestThreadScheduler4 {

    public static void main(String[] args) throws Exception {
        int loop = 100;
        Lock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();

        Thread t1 = new Thread(() -> {
            // 提前在try catch块前申请锁
            lock.lock();
            try {
                // 线程block，进入a条件队列，并释放持有锁
                a.await();
                for (int i = 0; i < loop; i++) {
                    System.out.println(Thread.currentThread().getName() + ": t1 run...@" + i);
                    // 通知b条件信号，不自动释放锁
                    b.signal();
                    if (i + 1 < loop) {
                        // 线程block，进入a条件队列，并释放持有锁
                        a.await();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                //异常时手动释放锁（只有程序被关闭了，finally才不会被执行）
                lock.unlock();
            }

        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                b.await();
                for (int i = 0; i < loop; i++) {
                    System.out.println(Thread.currentThread().getName() + ": t2 run...@" + i);
                    c.signal();
                    if (i + 1 < loop) {
                        b.await();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        });

        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                c.await();
                for (int i = 0; i < loop; i++) {
                    System.out.println(Thread.currentThread().getName() + ": t3 run...@" + i);
                    a.signal();
                    if (i + 1 < loop) {
                        c.await();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        });

        t1.start();
        t2.start();
        t3.start();

        // 开工
        lock.lock();
        // lock should locate before try catch block，
        // that means never end up unlocking someone else's lock when locking fails.
        try {
            a.signal();
        } finally {
            lock.unlock();
        }
    }
}
