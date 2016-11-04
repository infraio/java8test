package com.xiaohao.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorUtil {

  public static void shutdownGracefully(ExecutorService executor, int timeout) {
    try {
      System.out.println("attempt to shutdown executor");
      executor.shutdown();
      executor.awaitTermination(timeout, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      System.err.println("tasks interrupted");
    } finally {
      if (!executor.isTerminated()) {
        System.err.println("cancel non-finished tasks");
      }
      executor.shutdownNow();
      System.out.println("shutdown finished");
    }
  }
}
