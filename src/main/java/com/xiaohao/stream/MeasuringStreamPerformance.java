package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MeasuringStreamPerformance {

  public static final long SIZE = 10_000_000;

  public static void main(String[] args) {
    measurePerf(n -> iterativeSum(n), SIZE);
    measurePerf(n -> sequentialSum(n), SIZE);
    measurePerf(n -> parallelSum(n), SIZE);
    measurePerf(n -> rangeSum(n), SIZE);
    measurePerf(n -> parallelRangeSum(n), SIZE);
    measurePerf(n -> sideEffectSum(n), SIZE);
    measurePerf(n -> sideEffectParallelSum(n), SIZE);
    measurePerf(n -> forkJoinSum(n), SIZE);
  }

  public static void measurePerf(Function<Long, Long> f, long n) {
    List<Long> durations = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      long startTime = System.nanoTime();
      long sum = f.apply(n);
      long endTime = System.nanoTime();
      System.out.println("Result is " + sum);
      durations.add((endTime - startTime) / 1_000_000);
    }
    System.out.println(durations.stream().collect(Collectors.summarizingLong(d -> d)));
  }

  public static long iterativeSum(long n) {
    long sum = 0;
    for (long i = 0; i < n; i++) {
      sum += i;
    }
    return sum;
  }

  public static long sequentialSum(long n) {
    return Stream.iterate(0L, i -> i + 1).limit(n).reduce(0L, Long::sum);
  }

  public static long parallelSum(long n) {
    return Stream.iterate(0L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
  }

  public static long rangeSum(long n) {
    return LongStream.range(1, n).reduce(0L, Long::sum);
  }

  public static long parallelRangeSum(long n) {
    return LongStream.range(1, n).parallel().reduce(0L, Long::sum);
  }

  public static long sideEffectSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.range(1, n).forEach(accumulator::add);
    return accumulator.total;
  }

  public static long sideEffectParallelSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.range(1, n).parallel().forEach(accumulator::add);
    return accumulator.total;
  }

  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.range(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    return new ForkJoinPool().invoke(task);
  }
}

class Accumulator {
  public long total = 0;

  public void add(long value) {
    total += value;
  }
}