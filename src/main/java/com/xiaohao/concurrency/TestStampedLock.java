package com.xiaohao.concurrency;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class TestStampedLock {

  public static void main(String[] args) {
    testStampledLockTryConvertToWriteLock();
  }

  /**
   * Now this ut will dead lock, need more time to dig.
   */
  private static void testStampledLockTryConvertToWriteLock() {
    System.out.println("\ntestStampledLockTryConvertToWriteLock\n");
    StampedLock lock = new StampedLock();
    ExecutorService executor = Executors.newFixedThreadPool(24);
    Counter counter = new Counter();

    IntStream.range(0, 100000).forEach(i -> executor.submit(() -> {
      counter.incrementByConvertToWriteLock(lock);
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);
    System.out.println("Counter result is " + counter.getCount());
    assertEquals(100000, counter.getCount());
  }
}
