package com.xiaohao.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestExecutor {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    singleThreadExecutor.submit(() -> {
      String name = Thread.currentThread().getName();
      System.out.println("Thread is " + name);
    });
    ExecutorUtil.shutdownGracefully(singleThreadExecutor, 10);

    Callable<Integer> callable = () -> {
      TimeUnit.SECONDS.sleep(1);
      return 0;
    };

    ExecutorService fixedExecutor = Executors.newFixedThreadPool(1);
    Future<Integer> future = fixedExecutor.submit(callable);

    System.out.println("future done? " + future.isDone());
    Integer result = future.get();
    System.out.println("future done? " + future.isDone());
    System.out.println("result: " + result);

    Future<Integer> futureWithTimeout = fixedExecutor.submit(() -> {
      TimeUnit.SECONDS.sleep(2);
      return 0;
    });
    try {
      result = futureWithTimeout.get(1, TimeUnit.SECONDS);
    } catch (TimeoutException e) {
      System.out.println("Future timeout " + e);
    }
    ExecutorUtil.shutdownGracefully(fixedExecutor, 10);

    // invokeAll
    ExecutorService workStealingexecutor = Executors.newWorkStealingPool();
    List<Callable<String>> callables = Arrays.asList(() -> {
      return "task1";
    } , () -> {
      return "task2";
    } , () -> {
      return "task3";
    });
    workStealingexecutor.invokeAll(callables).stream().map(f -> {
      try {
        return f.get();
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }).forEach(System.out::println);

    // invokeAny
    callables = Arrays.asList(() -> "task1", () -> "task2", () -> "task3");
    String resultStr = workStealingexecutor.invokeAny(callables);
    System.out.println("invokeAny result is " + resultStr);

    callables = Arrays.asList(newCallable("task3", 3), newCallable("task2", 2),
      newCallable("task1", 1));
    resultStr = workStealingexecutor.invokeAny(callables);
    System.out.println("invokeAny result is " + resultStr);

    // Scheduled Executors
    ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> scheduledFuture = scheduledExecutor.schedule(
      () -> System.out.println("Current time is " + System.currentTimeMillis()), 3,
      TimeUnit.SECONDS);
    TimeUnit.SECONDS.sleep(1);
    System.out
        .println("Remaining delay is " + scheduledFuture.getDelay(TimeUnit.MILLISECONDS) + " ms");
    scheduledFuture.get();

    int initialDelay = 0;
    int period = 1;
    int delay = 1;
    scheduledExecutor.scheduleAtFixedRate(
      () -> System.out
          .println("schedule at fixed rate, current time is " + System.currentTimeMillis()),
      initialDelay, period, TimeUnit.SECONDS);

    scheduledExecutor.scheduleWithFixedDelay(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out
          .println("Schedule with fixed dleay, current time is " + System.currentTimeMillis());
    } , initialDelay, delay, TimeUnit.SECONDS);

    TimeUnit.SECONDS.sleep(10);
    ExecutorUtil.shutdownGracefully(scheduledExecutor, 10);
  }

  private static Callable<String> newCallable(String result, int sleepSeconds) {
    return () -> {
      TimeUnit.SECONDS.sleep(sleepSeconds);
      return result;
    };
  }
}
