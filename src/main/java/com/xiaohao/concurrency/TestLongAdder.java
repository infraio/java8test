package com.xiaohao.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class TestLongAdder {

  private static final int SIZE = 10000000;
  private static final int THREADS_NUM = 100;

  public static void main(String[] args) {
    testSynchronized();
    testLock();
    testAtomicLong();
    testLongAdder();
    testLongAccumulator();
    testSynchronized();
    testLock();
    testAtomicLong();
    testLongAdder();
    testLongAccumulator();
  }

  private static void testLongAdder() {
    ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
    LongAdder adder = new LongAdder();

    long startTime = System.currentTimeMillis();
    IntStream.range(0, SIZE).forEach(i -> executor.submit(() -> {
      adder.increment();
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);

    long endTime = System.currentTimeMillis();
    System.out.println(
      "Result is " + adder.longValue() + ", testLongAdder take " + (endTime - startTime) + " ms.");
  }

  private static void testAtomicLong() {
    ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
    AtomicLong atomic = new AtomicLong();

    long startTime = System.currentTimeMillis();
    IntStream.range(0, SIZE).forEach(i -> executor.submit(() -> {
      atomic.incrementAndGet();
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);

    long endTime = System.currentTimeMillis();
    System.out.println(
      "Result is " + atomic.get() + ", testAtomicLong take " + (endTime - startTime) + " ms.");
  }

  private static void testSynchronized() {
    ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
    Counter counter = new Counter();

    long startTime = System.currentTimeMillis();
    IntStream.range(0, SIZE).forEach(i -> executor.submit(() -> {
      counter.incrementSync();
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);

    long endTime = System.currentTimeMillis();
    System.out.println("Result is " + counter.getCount() + ", testSynchronized take "
        + (endTime - startTime) + " ms.");
  }

  private static void testLock() {
    ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
    Counter counter = new Counter();
    ReentrantLock lock = new ReentrantLock();

    long startTime = System.currentTimeMillis();
    IntStream.range(0, SIZE).forEach(i -> executor.submit(() -> {
      counter.incrementWithLock(lock);
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);

    long endTime = System.currentTimeMillis();
    System.out.println("Result is " + counter.getCount() + ", testLock take "
        + (endTime - startTime) + " ms.");
  }
  
  private static void testLongAccumulator() {
    ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
    LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);

    long startTime = System.currentTimeMillis();
    IntStream.range(0, SIZE).forEach(i -> executor.submit(() -> {
      accumulator.accumulate(1);
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);

    long endTime = System.currentTimeMillis();
    System.out.println(
      "Result is " + accumulator.longValue() + ", testLongAccumulator take " + (endTime - startTime) + " ms.");
  }
}
