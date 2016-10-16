package com.xiaohao.disruptor.perftest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public final class ValueAdditionQueueProcessor implements Runnable {

  private long value;
  private boolean running;
  private CountDownLatch latch;
  private final BlockingQueue<Long> queue;
  private long sequence;
  private final long count;
  
  public ValueAdditionQueueProcessor(BlockingQueue<Long> queue, long count) {
    this.queue = queue;
    this.count = count;
  }
  
  public long getValue() {
    return this.value;
  }
  
  public void reset(final CountDownLatch latch) {
    this.value = 0L;
    this.sequence = 0L;
    this.latch = latch;
  }
  
  public void halt() {
    running = false;
  }
  
  @Override
  public void run() {
    this.running = true;
    while (true) {
      try {
        value += queue.take();
        if (++sequence == count) {
          latch.countDown();
        }
      } catch (InterruptedException e) {
        if (!running) {
          break;
        }
      }
    }
  }

}
