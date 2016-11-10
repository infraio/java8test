package com.xiaohao.concurrency.async;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import com.xiaohao.concurrency.ExecutorUtil;

public class BestPriceFinder {

  List<Shop> shops;

  public BestPriceFinder() {
    shops = new ArrayList<>();
  }

  public void addShop(Shop shop) {
    shops.add(shop);
  }

  public List<String> findPrices1(String product) {
    return shops.stream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(Collectors.toList());
  }

  public List<String> findPrices2(String product) {
    return shops.parallelStream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(Collectors.toList());
  }

  public List<String> findPrices3(String product) {
    List<CompletableFuture<String>> futures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(
          () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
        .collect(Collectors.toList());
    return futures.stream().map(future -> future.join()).collect(Collectors.toList());
  }

  public List<String> findPrices4(String product, Executor executor) {
    List<CompletableFuture<String>> futures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(
          () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),
          executor))
        .collect(Collectors.toList());
    return futures.stream().map(future -> future.join()).collect(Collectors.toList());
  }

  public int getShopsNum() {
    return this.shops.size();
  }
  
  public static void addShops(BestPriceFinder finder, int shopsNum) {
    for (int i = 0; i < shopsNum; i++) {
      finder.addShop(new Shop("shop" + i));
    }
  }

  public static void testFinder(int shopsNum) {
    BestPriceFinder finder = new BestPriceFinder();
    addShops(finder, shopsNum);
    System.out.println("\nShops number is " + finder.getShopsNum() + "\n");

    long start = System.nanoTime();
    System.out.println(finder.findPrices1("iPhone"));
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");

    start = System.nanoTime();
    System.out.println(finder.findPrices2("iPhone"));
    duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");

    start = System.nanoTime();
    System.out.println(finder.findPrices3("iPhone"));
    duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");
    
    ExecutorService executor = Executors.newFixedThreadPool(Math.min(finder.getShopsNum(), 100));
    start = System.nanoTime();
    System.out.println(finder.findPrices4("iPhone", executor));
    duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");
    ExecutorUtil.shutdownGracefully(executor, 10);
  }

  public static void main(String[] args) {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    testFinder(availableProcessors);
    testFinder(availableProcessors + 1);
    testFinder(2 * availableProcessors + 1);
    testFinder(10 * availableProcessors);
  }

}
