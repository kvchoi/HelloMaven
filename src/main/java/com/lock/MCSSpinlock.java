package com.lock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class MCSSpinlock {

    // 指向最后一个申请锁的MCSNode
    volatile MCSNode queue;
    private static final AtomicReferenceFieldUpdater<MCSSpinlock, MCSNode> UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(MCSSpinlock.class, MCSNode.class, "queue");

    public void lock(MCSNode currentThread) {
        MCSNode predecessor = UPDATER.getAndSet(this, currentThread);
        // step 1
        if (predecessor != null) {
            // step 2
            predecessor.next = currentThread;
            // step 3
            while (currentThread.isBlock) {
                //spin
            }
        } else {
            // 只有一个线程在使用锁，没有前驱来通知它，所以得自己标记自己为非阻塞
            currentThread.isBlock = false;
        }
    }

    public void unlock(MCSNode currentThread) {
        if (currentThread.isBlock) {
            // 锁拥有者进行释放锁才有意义
            return;
        }
        if (currentThread.next == null) {
            // 检查是否有人排在自己后面
            if (UPDATER.compareAndSet(this, currentThread, null)) {
                // step 4
                // compareAndSet返回true表示确实没有人排在自己后面
                return;
            } else {
                // 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
                // 这里之所以要忙等是因为：step 1执行完后，step 2可能还没执行完
                while (currentThread.next == null) {
                    // step 5
                }
            }
        }
        currentThread.next.isBlock = false;
        // for GC
        currentThread.next = null;
    }

    public static class MCSNode {
        volatile MCSNode next;
        // 默认是在等待锁
        volatile boolean isBlock = true;
    }
}