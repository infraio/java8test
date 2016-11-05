package com.xiaohao.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TestSemaphore {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    Semaphore semaphore = new Semaphore(5);
    
    IntStream.range(0, 10).forEach(i -> executor.submit(() -> {
      boolean permit = false;
      try {
        permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
        if (permit) {
          System.out.println("Semaphore acquired");
          ExecutorUtil.sleep(5);
        } else {
          System.out.println("Could not acquire semaphore");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (permit) {
          semaphore.release();
        }
      }
    }));

    ExecutorUtil.shutdownGracefully(executor, 10);
  }

}
