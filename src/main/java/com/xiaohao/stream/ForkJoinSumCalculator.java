package com.xiaohao.stream;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

  long[] numbers;
  int start;
  int end;
  
  public ForkJoinSumCalculator(long[] numbers) {
    this(numbers, 0, numbers.length);
  }
  
  public ForkJoinSumCalculator(long[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    if (end - start < 10000) {
      return computesequential();
    }
    
    int mid = (start + end) / 2;
    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, mid);
    leftTask.fork();
    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, mid, end);
    Long rightResult = rightTask.compute();
    Long leftResult = leftTask.join();
    return leftResult + rightResult;
  }

  private long computesequential() {
    long sum = 0;
    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }
    return sum;
  }
}
