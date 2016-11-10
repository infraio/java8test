package com.xiaohao.concurrency.async;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Shop {

  private Random random;
  private String name;

  public Shop(String name) {
    this.name = name;
    random = new Random(System.currentTimeMillis());
  }

  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public Future<Double> getPriceAsync1(String product) {
    CompletableFuture<Double> future = new CompletableFuture<>();
    new Thread(() -> {
      try {
        double price = calculatePrice(product);
        future.complete(price);
      } catch (Exception e) {
        future.completeExceptionally(e);
      }
    }).start();
    return future;
  }

  public Future<Double> getPriceAsync2(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }

  private double calculatePrice(String product) {
    delay();
    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }

  private void delay() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  public String getName() {
    return this.name;
  }
}
