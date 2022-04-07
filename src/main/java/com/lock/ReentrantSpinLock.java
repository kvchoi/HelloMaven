package com.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 1) 支持可重入，判断是否是重入，重入不需要改变同步状态，而只需要计数
 *
 * 2）确保只有锁的拥有者才能做unlock
 *
 * @author cairongfu
 */
public class ReentrantSpinLock {
    // use thread itself as  synchronization state
    private AtomicReference<Thread> owner = new AtomicReference<>();
    // reentrant count of a thread, no need to be volatile
    private int count = 0;

    public void lock() {
        Thread t = Thread.currentThread();
        if (t == owner.get()) {
			// if re-entered, increment the count.
            ++count;
            return;
        }
        while (owner.compareAndSet(null, t)) {
            //spin
        }
    }

    public void unlock() {
        Thread t = Thread.currentThread();
        //only the owner could do unlock
        if (t == owner.get()) {
            if (count > 0) {
				// reentrant count not zero, just decrease the counter.
				--count;
			} else {
                owner.set(null);
				// compareAndSet is not need here, already checked
            }
        }
    }
}