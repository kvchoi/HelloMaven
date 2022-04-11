package com.common.threads;

import java.util.concurrent.TimeUnit;

public class TestExceptionInChildThread {

    /**
     * 模拟子线程发生异常
     *
     * @throws InterruptedException
     */
    @SuppressWarnings("uncheck")
    private static void exceptionThread() throws InterruptedException {
        new Thread(() -> {
            throw new RuntimeException("run()发生异常");
        }).start();
        TimeUnit.MILLISECONDS.sleep(200L);

        new Thread(() -> {
            throw new RuntimeException("run()发生异常");
        }).start();
        TimeUnit.MILLISECONDS.sleep(200L);

        new Thread(() -> {
            throw new RuntimeException("run()发生异常");
        }).start();
        TimeUnit.MILLISECONDS.sleep(200L);

        new Thread(() -> {
            throw new RuntimeException("run()发生异常");
        }).start();
        TimeUnit.MILLISECONDS.sleep(200L);
    }

    public static void main(String[] args) throws InterruptedException {
        //设置全局的线程异常处理器
        Thread.setDefaultUncaughtExceptionHandler(new CustomThreadUncaughtExceptionHandler());
        exceptionThread();
    }
}