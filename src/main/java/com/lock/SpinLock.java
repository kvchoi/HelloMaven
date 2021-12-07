package com.lock;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLock {

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
