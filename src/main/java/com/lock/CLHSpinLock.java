package com.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author cairongfu
 */
public class CLHSpinLock {

	AtomicReference<CLHNode> tail;
	ThreadLocal<CLHNode> myPred;
	ThreadLocal<CLHNode> myNode;
 
	public CLHSpinLock() {
		tail = new AtomicReference<>(new CLHNode());
		myNode = ThreadLocal.withInitial(CLHNode::new);
		myPred = ThreadLocal.withInitial(() -> null);
	}
 
	public void lock() {
		CLHNode clhNode = myNode.get();
		clhNode.locked = true;
		CLHNode pred = tail.getAndSet(clhNode);
		myPred.set(pred);
		while (pred.locked) {
			//spin
		}
	}
 
	public void unlock() {
		CLHNode clhNode = myNode.get();
		clhNode.locked = false;
		myNode.set(myPred.get());
	}

	private class CLHNode {
		private volatile boolean locked = false;
	}
}