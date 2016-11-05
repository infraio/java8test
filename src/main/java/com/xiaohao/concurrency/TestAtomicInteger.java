package com.xiaohao.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TestAtomicInteger {

  public static void main(String[] args) {
    AtomicInteger atomicInt = new AtomicInteger(0);
    ExecutorService executor = Executors.newFixedThreadPool(4);
    
    IntStream.range(0, 10000).forEach(i -> executor.submit(() -> {
      atomicInt.incrementAndGet();
    }));
    
    ExecutorUtil.sleep(1);
    System.out.println("Result is " + atomicInt.get());
    
    atomicInt.set(0);
    IntStream.range(0, 10000).forEach(i -> executor.submit(() -> {
      atomicInt.updateAndGet(n -> n + 2);
    }));
    
    ExecutorUtil.sleep(1);
    System.out.println("Result is " + atomicInt.get());
    
    atomicInt.set(0);
    IntStream.range(0, 10000).forEach(i -> executor.submit(() -> {
      atomicInt.accumulateAndGet(i, (x, y) -> x + y);
    }));

    ExecutorUtil.sleep(1);
    System.out.println("Result is " + atomicInt.get());
    
    ExecutorUtil.shutdownGracefully(executor, 10);
  }

}
