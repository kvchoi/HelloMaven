package com.common.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomThreadUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.error("自定义异常处理器，捕获到线程发生的异常，当前线程:[{}], 执行线程:[{}], 异常信息:[{}]",
                Thread.currentThread().getName(), t.getName(), e.getMessage());
    }

}
