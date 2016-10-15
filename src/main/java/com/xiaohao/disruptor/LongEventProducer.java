package com.xiaohao.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class LongEventProducer {
  private final RingBuffer<LongEvent> ringBuffer;

  public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  public void onData(ByteBuffer byteBuffer) {
    // Grab the next sequence
    long sequence = ringBuffer.next();
    try {
      // Get the entry in the ring buffer for the sequence
      LongEvent event = ringBuffer.get(sequence);
      // Fill with data
      event.set(byteBuffer.getLong(0));
    } finally {
      ringBuffer.publish(sequence);
    }
  }

}