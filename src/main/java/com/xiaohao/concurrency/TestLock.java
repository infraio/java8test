package com.xiaohao.concurrency;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class TestLock {

  public static void main(String[] args) {
    testReentrantLock();
    testReadWriteLock();
    testStampedLock();
  }

  private static void testReentrantLock() {
    System.out.println("\ntestReentrantLock\n");
    ExecutorService executor = Executors.newFixedThreadPool(24);
    ReentrantLock lock = new ReentrantLock();

    executor.submit(() -> {
      lock.lock();
      try {
        ExecutorUtil.sleep(1);
      } finally {
        lock.unlock();
      }
    });

    executor.submit(() -> {
      System.out.println("Locked: " + lock.isLocked());
      System.out.println("Held by me: " + lock.isHeldByCurrentThread());
      boolean locked = lock.tryLock();
      System.out.println("Lock acquired: " + locked);
    });

    Counter counter = new Counter();
    IntStream.range(0, 1000000).forEach(i -> executor.submit(() -> {
      counter.incrementWithLock(lock);
    }));
    ExecutorUtil.shutdownGracefully(executor, 10);
    System.out.println("Counter result is " + counter.getCount());
    assertEquals(1000000, counter.getCount());
  }

  private static void testReadWriteLock() {
    System.out.println("\ntestReadWriteLock\n");
    ReadWriteLock lock = new ReentrantReadWriteLock();
    ExecutorService executor = Executors.newFixedThreadPool(2);
    Map<String, String> map = new HashMap<>();

    // Read and Write test
    executor.submit(() -> {
      lock.writeLock().lock();
      try {
        System.out.println("Current time is " + System.currentTimeMillis());
        map.put("lock", "ReadWriteLock");
        ExecutorUtil.sleep(1);
      } finally {
        lock.writeLock().unlock();
      }
    });

    Runnable readTask = () -> {
      lock.readLock().lock();
      try {
        System.out.println(
          "Current time is " + System.currentTimeMillis() + ", get lock " + map.get("lock"));
      } finally {
        lock.readLock().unlock();
      }
    };

    executor.submit(readTask);
    executor.submit(readTask);
    executor.submit(readTask);

    ExecutorUtil.shutdownGracefully(executor, 10);
  }

  private static void testStampedLock() {
    System.out.println("\ntestStampedLock\n");

    StampedLock lock = new StampedLock();
    ExecutorService executor = Executors.newFixedThreadPool(24);
    Counter counter = new Counter();
    IntStream.range(0, 1000000).forEach(i -> executor.submit(() -> {
      counter.incrementWithStampedLock(lock);
    }));
    ExecutorUtil.shutdownGracefully(executor, 60);
    System.out.println("Counter result is " + counter.getCount());
    assertEquals(1000000, counter.getCount());

    testReadWriteWithStampedLock();
    testStampedLockTryOptimisticRead();
  }

  private static void testStampedLockTryOptimisticRead() {
    System.out.println("\ntestStampedLockTryOptimisticRead\n");
    StampedLock lock = new StampedLock();
    ExecutorService executor = Executors.newFixedThreadPool(2);

    // tryOptimisticRead
    executor.submit(() -> {
      long stamp = lock.tryOptimisticRead();
      try {
        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
        ExecutorUtil.sleep(1);
        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
        ExecutorUtil.sleep(2);
        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
      } finally {
        lock.unlock(stamp);
      }
    });

    executor.submit(() -> {
      long stamp = lock.writeLock();
      try {
        System.out.println("Write Lock acquired");
        ExecutorUtil.sleep(2);
      } finally {
        lock.unlock(stamp);
        System.out.println("Write done");
      }
    });

    ExecutorUtil.shutdownGracefully(executor, 10);
  }

  private static void testReadWriteWithStampedLock() {
    System.out.println("\ntestReadWriteWithStampedLock\n");
    StampedLock lock = new StampedLock();
    ExecutorService executor = Executors.newFixedThreadPool(2);
    Map<String, String> map = new HashMap<>();

    executor.submit(() -> {
      long stamp = lock.writeLock();
      try {
        System.out.println("Current time is " + System.currentTimeMillis() + ", stamp is " + stamp);
        map.put("lock", "StampedLock");
        ExecutorUtil.sleep(1);
      } finally {
        lock.unlockWrite(stamp);
      }
    });

    Runnable readTask = () -> {
      long stamp = lock.readLock();
      try {
        System.out.println("Current time is " + System.currentTimeMillis() + ", stamp is " + stamp
            + ", get lock " + map.get("lock"));
      } finally {
        lock.unlockRead(stamp);
      }
    };

    executor.submit(readTask);
    executor.submit(readTask);
    executor.submit(readTask);

    ExecutorUtil.shutdownGracefully(executor, 10);
  }
}
