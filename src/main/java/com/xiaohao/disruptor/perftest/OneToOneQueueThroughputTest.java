package com.xiaohao.disruptor.perftest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.lmax.disruptor.util.DaemonThreadFactory;

public final class OneToOneQueueThroughputTest extends AbstractPerfTestQueue {

  private static final int BUFFER_SIZE = 1024 * 64;
  private static final long ITERATIONS = 1000L * 1000L * 10L;
  private final ExecutorService executor =
      Executors.newSingleThreadExecutor(DaemonThreadFactory.INSTANCE);
  private final long expectedResult = PerfTestUtil.accumulatedAddition(ITERATIONS);

  private final BlockingQueue<Long> blockingQueue = new LinkedBlockingQueue<Long>(BUFFER_SIZE);
  private final ValueAdditionQueueProcessor queueProcessor =
      new ValueAdditionQueueProcessor(blockingQueue, ITERATIONS);

  @Override
  protected int getRequiredProcessorCount() {
    return 2;
  }

  @Override
  protected long runQueuePass() throws Exception {
    final CountDownLatch latch = new CountDownLatch(1);
    queueProcessor.reset(latch);
    Future<?> future = executor.submit(queueProcessor);

    long start = System.currentTimeMillis();

    for (long i = 0; i < ITERATIONS; i++) {
      blockingQueue.put(i);
    }

    latch.await();
    long opsPerSecond = (ITERATIONS * 1000L) / (System.currentTimeMillis() - start);
    queueProcessor.halt();
    future.cancel(true);

    PerfTestUtil.failIfNot(expectedResult, queueProcessor.getValue());

    return opsPerSecond;
  }

  public static void main(String[] args) throws Exception {
    OneToOneQueueThroughputTest test = new OneToOneQueueThroughputTest();
    test.testImplementations();
  }
}
