package com.xiaohao.disruptor.perftest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * <pre>
 * UniCast a series of items between 1 publisher and 1 event processor.
 *
 * +----+    +-----+
 * | P1 |--->| EP1 |
 * +----+    +-----+
 *
 * Disruptor:
 * ==========
 *              track to prevent wrap
 *              +------------------+
 *              |                  |
 *              |                  v
 * +----+    +====+    +====+   +-----+
 * | P1 |--->| RB |<---| SB |   | EP1 |
 * +----+    +====+    +====+   +-----+
 *      claim      get    ^        |
 *                        |        |
 *                        +--------+
 *                          waitFor
 *
 * P1  - Publisher 1
 * RB  - RingBuffer
 * SB  - SequenceBarrier
 * EP1 - EventProcessor 1
 *
 * </pre>
 */
public final class OneToOneSequencedThroughputTest extends AbstractPerfTestDisruptor {

  private static final int BUFFER_SIZE = 1024 * 64;
  private static final long ITERATIONS = 1000L * 1000L * 100L;
  private final ExecutorService executor =
      Executors.newSingleThreadExecutor(DaemonThreadFactory.INSTANCE);
  private final long expectedResult = PerfTestUtil.accumulatedAddition(ITERATIONS);

  private final RingBuffer<ValueEvent> ringBuffer = RingBuffer
      .createSingleProducer(ValueEvent.EVENT_FACTORY, BUFFER_SIZE, new YieldingWaitStrategy());
  private final SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
  private final ValueAdditionEventHandler handler = new ValueAdditionEventHandler();
  private final BatchEventProcessor<ValueEvent> batchEventProcessor =
      new BatchEventProcessor<ValueEvent>(ringBuffer, sequenceBarrier, handler);

  {
    ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
  }

  @Override
  protected int getRequiredProcessorCount() {
    return 2;
  }

  @Override
  protected long runDisruptorPass() throws Exception {
    final CountDownLatch latch = new CountDownLatch(1);
    long expectedCount = batchEventProcessor.getSequence().get() + ITERATIONS;
    handler.reset(latch, expectedCount);
    executor.submit(batchEventProcessor);

    long start = System.currentTimeMillis();

    RingBuffer<ValueEvent> rb = ringBuffer;
    for (long i = 0; i < ITERATIONS; i++) {
      long next = rb.next();
      rb.get(next).setValue(i);
      rb.publish(next);
    }

    latch.await();
    long opsPerSecond = (ITERATIONS * 1000L) / (System.currentTimeMillis() - start);

    waitForEventProcessorSequence(expectedCount);
    batchEventProcessor.halt();
    PerfTestUtil.failIfNot(expectedResult, handler.getValue());

    return opsPerSecond;
  }

  private void waitForEventProcessorSequence(long expectedCount) throws InterruptedException {
    while (batchEventProcessor.getSequence().get() != expectedCount) {
      Thread.sleep(1);
    }
  }

  public static void main(String[] args) throws Exception {
    OneToOneSequencedThroughputTest test = new OneToOneSequencedThroughputTest();
    test.testImplementations();
  }
}
