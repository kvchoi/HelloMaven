package com.common.threads;

/**
 * "创建线程时"会去关联父线程创建local map，并会把父线程中的值都拷贝过来。
 * 所以如果父线程如果重新set了，对子线程不可见，子线程set了，对父线程也不可见，因为不是同一个map。
 * <p>
 *      if (inheritThreadLocals && parent.inheritableThreadLocals != null)
 *             this.inheritableThreadLocals = ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
 * </p>
 */
public class TestInhertableThreadLocal {

    public static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello");
        System.out.println(threadLocal.get());

        new Thread() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println(threadLocal.get());
                    }
                }.start();
            }
        }.start();
    }
}
