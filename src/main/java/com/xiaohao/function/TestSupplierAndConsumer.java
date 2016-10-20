package com.xiaohao.function;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestSupplierAndConsumer {
  
  public static final long SIZE = 1000;

  public static void main(String[] args) throws InterruptedException {
    Supplier<LongEvent> supplier = LongEvent::new;
    System.out.println(supplier.get().setValue(123));
    
    Consumer<LongEvent> consumer = event -> System.out.println("Consume " + event);
    consumer.accept(new LongEvent(456));
    
    BlockingQueue<LongEvent> queue = new LinkedBlockingQueue<LongEvent>();
    CountDownLatch latch = new CountDownLatch(1);
    
    Thread supplierThread = new Thread(new Runnable() {

      @Override
      public void run() {
        for (int i = 0; i < SIZE; i++) {
          queue.add(supplier.get().setValue(i));
        }
      }
      
    });
    
    Thread consumerThread = new Thread(new Runnable() {

      @Override
      public void run() {
        while (true) {
          try {
            LongEvent event = queue.take();
            consumer.accept(event);
            if (event.getValue() == SIZE - 1) {
              latch.countDown();
              break;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      
    });
    
    supplierThread.start();
    consumerThread.start();
    latch.await();
  }

}

class LongEvent {
  private long value;
  
  public LongEvent() {
    this.value = 0;
  }
  
  public LongEvent(long value) {
    this.value = value;
  }
  
  public LongEvent setValue(int value) {
    this.value = value;
    return this;
  }
  
  public long getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}