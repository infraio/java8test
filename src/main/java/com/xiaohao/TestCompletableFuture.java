package com.xiaohao;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TestCompletableFuture {

  public static void main(String[] args) {
    List<CompletableFuture<Double>> futures = Arrays.asList(1, 2, 3, 4).stream()
        .map(i -> CompletableFuture.supplyAsync(() -> i * 1.0))
        .map(future -> future.thenCombine(CompletableFuture.supplyAsync(() -> 2.0), (a, b) -> {
          double result = a * b;
          return result;
        })).collect(Collectors.toList());
    futures.stream().map(future -> future.join())
        .forEach(future -> System.out.println(future.doubleValue()));
  }

}
