package com.xiaohao.concurrency.async;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AsyncUtil {

  private static final Random random = new Random();

  public static void randomDelay() {
    int delay = 500 + random.nextInt(2000);
    try {
      TimeUnit.MILLISECONDS.sleep(delay);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void delay() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
