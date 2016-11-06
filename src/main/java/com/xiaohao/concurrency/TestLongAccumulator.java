package com.xiaohao.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class TestLongAccumulator {

  public static void main(String[] args) {
    LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 1);
    ExecutorService oneThreadExecutor = Executors.newFixedThreadPool(1);

    IntStream.range(0, 10).forEach(i -> oneThreadExecutor.submit(() -> {
      long value = accumulator.get();
      System.out.println("Current value is " + value);
      accumulator.accumulate(value);
    }));

    ExecutorUtil.shutdownGracefully(oneThreadExecutor, 10);
    System.out.println("One thread result is " + accumulator.get());

    ExecutorService twoThreadExecutor = Executors.newFixedThreadPool(2);
    accumulator.reset();

    IntStream.range(0, 10).forEach(i -> twoThreadExecutor.submit(() -> {
      long value = accumulator.get();
      System.out.println("Current value is " + value);
      accumulator.accumulate(value);
    }));

    ExecutorUtil.shutdownGracefully(twoThreadExecutor, 10);
    System.out.println("Two thread result is " + accumulator.get());

    ExecutorService fourThreadExecutor = Executors.newFixedThreadPool(4);
    accumulator.reset();
    ReentrantLock lock = new ReentrantLock();
    
    IntStream.range(0, 10).forEach(i -> fourThreadExecutor.submit(() -> {
      lock.lock();
      try {
      long value = accumulator.get();
      System.out.println("Current value is " + value);
      accumulator.accumulate(value);
      } finally {
        lock.unlock();
      }
    }));

    ExecutorUtil.shutdownGracefully(fourThreadExecutor, 10);
    System.out.println("Four thread result is " + accumulator.get());

    ExecutorService eightThreadExecutor = Executors.newFixedThreadPool(4);
    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
    
    IntStream.range(0, 10000).forEach(i -> eightThreadExecutor.submit(() -> {
      longAccumulator.accumulate(i);
    }));

    ExecutorUtil.shutdownGracefully(eightThreadExecutor, 10);
    System.out.println("Eight thread result is " + longAccumulator.get());
  }

}
