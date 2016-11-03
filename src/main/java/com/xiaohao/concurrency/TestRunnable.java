package com.xiaohao.concurrency;

import java.util.concurrent.TimeUnit;

public class TestRunnable {

  public static void main(String[] args) {
    Runnable task = () -> {
      String threadName = Thread.currentThread().getName();
      System.out.println("Hello " + threadName);
    };

    task.run();

    Thread thread = new Thread(task);
    thread.start();

    System.out.println("Done!");
    
    Runnable runnable = () -> {
      String name = Thread.currentThread().getName();
      System.out.println("First " + name);
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Second " + name);
    };
    
    Thread thread1 = new Thread(runnable);
    thread1.start();
  }

}
