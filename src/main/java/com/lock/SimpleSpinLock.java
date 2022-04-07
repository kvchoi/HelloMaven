package com.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 简单自旋锁
 *
 * @author cairongfu
 */
public class SimpleSpinLock {

    private final AtomicBoolean spinLock = new AtomicBoolean(false);

    public boolean lock() {
        boolean flag;
        do {
            flag = this.spinLock.compareAndSet(false, true);
        } while (!flag);
        return true;
    }

    public boolean unlock() {
        return this.spinLock.compareAndSet(true, false);
    }

    public boolean islock() {
        return this.spinLock.get();
    }
}
