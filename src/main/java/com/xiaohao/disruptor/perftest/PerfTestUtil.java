package com.xiaohao.disruptor.perftest;

public final class PerfTestUtil {
  public static long accumulatedAddition(final long iterations) {
    long temp = 0L;
    for (long i = 0L; i < iterations; i++) {
      temp += i;
    }

    return temp;
  }

  public static void failIf(long a, long b) {
    if (a == b) {
      throw new RuntimeException();
    }
  }

  public static void failIfNot(long a, long b) {
    if (a != b) {
      throw new RuntimeException("execpted value is " + a + ", but actual value is b");
    }
  }
}
