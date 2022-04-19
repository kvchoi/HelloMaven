package com.common.threads.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestExceptionInThreadGroup {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestExceptionInThreadPool.class);

    private static final ThreadGroup threadGroup = new ThreadGroup("只知道抛出异常的线程组...") {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            super.uncaughtException(t, e);
            LOGGER.error("当前线程[{}]，线程组内捕获到线程[{},{}]异常",
                    Thread.currentThread().getName(), t.getId(), t.getName(), e);
        }
    };

    public static void main(String[] args) throws InterruptedException {

        new Thread(threadGroup, () -> {
            throw new RuntimeException("run()发生异常");
        }, "线程1").start();
        TimeUnit.MILLISECONDS.sleep(300L);

        //优先使用绑定在thread对象上的异常处理器
        Thread thread = new Thread(threadGroup, () -> {
            throw new RuntimeException("run()发生异常");
        }, "线程2");
        thread.setUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(300L);

        new Thread(threadGroup, () -> {
            throw new RuntimeException("run()发生异常");
        }, "线程3").start();
        TimeUnit.MILLISECONDS.sleep(300L);
    }
}
