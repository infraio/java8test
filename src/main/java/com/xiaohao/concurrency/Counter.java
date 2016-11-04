package com.xiaohao.concurrency;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Counter {
  private int count;

  public Counter() {
    this.count = 0;
  }

  public void increment() {
    count = count + 1;
  }

  public synchronized void incrementSync() {
    increment();
  }

  public void incrementWithLock(ReentrantLock lock) {
    lock.lock();
    try {
      increment();
    } finally {
      lock.unlock();
    }
  }

  public void incrementWithStampedLock(StampedLock lock) {
    long stamp = lock.writeLock();
    try {
      increment();
    } finally {
      lock.unlock(stamp);
    }
  }

  public void incrementByConvertToWriteLock(StampedLock lock) {
    long stamp = lock.readLock();
    try {
      System.out.println("read lock stamp is " + stamp + " by thread " + Thread.currentThread().getName());
      stamp = lock.tryConvertToWriteLock(stamp);
      if (stamp == 0) {
        System.out.println("Could not convert to write lock");
        stamp = lock.writeLock();
      }
      increment();
    } finally {
      System.out.println("write lock stamp is " + stamp + " by thread " + Thread.currentThread().getName());
      lock.unlockWrite(stamp);
    }
  }
  
  public int getCount() {
    return this.count;
  }
}
