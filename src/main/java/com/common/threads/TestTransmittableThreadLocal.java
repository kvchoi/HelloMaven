package com.common.threads;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 但对于使用线程池等会池化复用线程的执行组件的情况，线程由线程池创建好，并且线程是池化起来反复使用的；
 * <p>
 * 这时通过new Thread创建父子线程关系的ThreadLocal值传递已经没有意义；
 * <p>
 * 应用需要的实际上是把任务*提交给线程池时*的ThreadLocal值传递到*任务实际执行时*的具体线程中。
 *
 * @author cairongfu
 */
public class TestTransmittableThreadLocal {

    /**
     * 定义一个通用执行器
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);
    /**
     * 定义一个字符型线程变量操作器
     */
    private static final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {

        // 在父线程中设置
        context.set("value-set-in-parent");
        Runnable task = new RunnableTask();

        // 额外的处理，生成修饰了的对象ttlRunnable
        Runnable ttlRunnable = TtlRunnable.get(task, true);
        assert ttlRunnable != null;
        executorService.submit(ttlRunnable);

        // 尝试读取
        TimeUnit.SECONDS.sleep(1);
        String value = context.get();
        System.out.println(value);

        // 退出程序
        System.out.println("main thread exit...");
        executorService.shutdown();
    }

    private static class RunnableTask implements Runnable {

        @Override
        public void run() {
            // Task中可以读取，值是"value-set-in-parent"
            String value = context.get();
            System.out.println(value);

            // Task中重新set，无效
            context.set("value-set-in-child");
        }
    }
}
