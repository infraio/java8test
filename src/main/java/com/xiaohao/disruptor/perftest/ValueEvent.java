package com.xiaohao.disruptor.perftest;

import com.lmax.disruptor.EventFactory;

public final class ValueEvent {
  private long value;

  public long getValue() {
    return this.value;
  }

  public void setValue(final long value) {
    this.value = value;
  }

  public static final EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {

    @Override
    public ValueEvent newInstance() {
      return new ValueEvent();
    }

  };
}
