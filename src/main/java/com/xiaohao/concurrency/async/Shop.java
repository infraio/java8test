package com.xiaohao.concurrency.async;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

  private Random random;
  private String name;

  public Shop(String name) {
    this.name = name;
    random = new Random();
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

  public String getPriceWithDiscount(String product) {
    double price = calculatePrice(product);
    Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
    return String.format("%s:%.2f:%s", name, price, code);
  }

  public String getPriceWithRandomDelay(String product) {
    double price = calculatePriceWithRandomDelay(product);
    Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
    return String.format("%s:%.2f:%s", name, price, code);
  }
  
  private double calculatePrice(String product) {
    AsyncUtil.delay();
    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }
  
  private double calculatePriceWithRandomDelay(String product) {
    AsyncUtil.randomDelay();
    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }
  
  public String getName() {
    return this.name;
  }
}
