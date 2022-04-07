package com.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 排队自旋锁（保证FIFO，先取号的肯定先进入，保证公平性）
 *
 * @author cairongfu
 */
public class TicketSpinLock {

    private final AtomicInteger serviceNum = new AtomicInteger(0);
    private final AtomicInteger ticketNum = new AtomicInteger(0);
    private static final ThreadLocal<Integer> myNum = new ThreadLocal<>();

    public Integer lock() {
        //take the current ticket number, which the formal guy will notify at least
        // 排队者中途退出（stop/exit），会存在废号问题？
        myNum.set(ticketNum.getAndIncrement());
        //wait until call out
        while (serviceNum.get() != myNum.get()) {
            // spin
        }
		return myNum.get();
    }

    public void unlock() {
        if (myNum.get() != null && serviceNum.get() == myNum.get()) {
            // call next number, and release my ticket
            serviceNum.compareAndSet(myNum.get(), myNum.get() + 1);
			myNum.remove();
		}

    }
}