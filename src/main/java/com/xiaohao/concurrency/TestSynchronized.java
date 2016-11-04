package com.xiaohao.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSynchronized {

  private static final int COUNT = 10000000;
  private static final int TIMEOUT = 600;
  private static final int THREADS_NUM = 24;

  public static void main(String[] args) {
    testSync();
    testNoSync();
    testSync();
    testNoSync();
    testSync();
    testNoSync();
    testSync();
    testNoSync();
    testSync();
    testNoSync();
  }

  private static void testSync() {
    ExecutorService executorSync = Executors.newFixedThreadPool(THREADS_NUM);
    Counter counterSync = new Counter();
    long startTime = System.currentTimeMillis();
    IntStream.range(0, COUNT).forEach(i -> executorSync.submit(() -> {
      counterSync.incrementSync();
    }));
    ExecutorUtil.shutdownGracefully(executorSync, TIMEOUT);
    long endTime = System.currentTimeMillis();
    System.out.println("CounterSync result is " + counterSync.getCount() + ", take "
        + (endTime - startTime) + " ms");
    assertEquals(COUNT, counterSync.getCount());
  }

  private static void testNoSync() {
    Counter counter = new Counter();
    ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
    long startTime = System.currentTimeMillis();
    IntStream.range(0, COUNT).forEach(i -> executor.submit(() -> {
      counter.increment();
    }));
    ExecutorUtil.shutdownGracefully(executor, TIMEOUT);
    long endTime = System.currentTimeMillis();
    System.out.println(
      "Counter result is " + counter.getCount() + ", take " + (endTime - startTime) + " ms");
    assertTrue(counter.getCount() <= COUNT);
  }
}