package com.lock;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class MyBenchmark {
    int i;
    final SimpleSpinLock lock = new SimpleSpinLock();
    final Object object = new Object();

    @Setup
    public void setup() {
        i = 0;
    }

    @TearDown
    public void teardown() {
        System.out.println("lock value = " + lock.islock());
        System.out.println("i = " + i);
    }

    @Benchmark
    @Threads(5)
    public void testSpin() {
        if (lock.lock()) {
            i += 1;
            lock.unlock();
        }
    }

    @Benchmark
    @Threads(5)
    public void testObject() {
        synchronized (object) {
            i += 1;
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(MyBenchmark.class.getName()).build();
        new Runner(opt).run();
    }

}