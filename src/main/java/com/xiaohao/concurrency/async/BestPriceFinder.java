package com.xiaohao.concurrency.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

  public List<String> findPricesWithDiscount1(String product) {
    return shops.stream().map(shop -> shop.getPriceWithDiscount(product)).map(Quote::parse)
        .map(Discount::applyDiscount).collect(Collectors.toList());
  }

  public List<String> findPricesWithDiscount2(String product, Executor executor) {
    List<CompletableFuture<String>> futures = shops.stream()
        .map(
          shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscount(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        .map(future -> future.thenCompose(
          quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
        .collect(Collectors.toList());
    return futures.stream().map(future -> future.join()).collect(Collectors.toList());
  }

  public List<CompletableFuture<String>> findPricesStream(String product, Executor executor) {
    return shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithRandomDelay(product),
          executor))
        .map(future -> future.thenApply(Quote::parse))
        .map(future -> future.thenCompose(quote -> CompletableFuture
            .supplyAsync(() -> Discount.applyDiscountWithRandomDelay(quote), executor)))
        .collect(Collectors.toList());
  }

  public List<Double> findPricesInCNY(String product, Executor executor) {
    List<CompletableFuture<Double>> futuresPriceInCNY = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
        .map(future -> future.thenCombine(
          CompletableFuture.supplyAsync(
            () -> ExchangeService.getRate(ExchangeService.Money.CNY, ExchangeService.Money.USD)),
          (price, rate) -> {
            double result = price * rate;
            return result;
          }))
        .collect(Collectors.toList());

    return futuresPriceInCNY.stream().map(future -> future.join()).collect(Collectors.toList());
  }

  public int getShopsNum() {
    return this.shops.size();
  }

  public static void addShops(BestPriceFinder finder, int shopsNum) {
    for (int i = 0; i < shopsNum; i++) {
      finder.addShop(new Shop("shop" + i));
    }
  }

  public static void testFindPrice(int shopsNum) {
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

  public static void testFindPriceWithDiscount(int shopsNum) {
    BestPriceFinder finder = new BestPriceFinder();
    addShops(finder, shopsNum);
    System.out.println("\nShops number is " + finder.getShopsNum() + "\n");

    long start = System.nanoTime();
    System.out.println(finder.findPricesWithDiscount1("iPhone"));
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");

    ExecutorService executor = Executors.newFixedThreadPool(Math.min(finder.getShopsNum(), 100));
    start = System.nanoTime();
    System.out.println(finder.findPricesWithDiscount2("iPhone", executor));
    duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Done in " + duration + " msecs");
    ExecutorUtil.shutdownGracefully(executor, 10);
  }

  public static void testFindPricesStream(int shopsNum) {
    BestPriceFinder finder = new BestPriceFinder();
    addShops(finder, shopsNum);
    System.out.println("\nShops number is " + finder.getShopsNum() + "\n");

    ExecutorService executor = Executors.newFixedThreadPool(Math.min(finder.getShopsNum(), 100));
    long start = System.nanoTime();
    CompletableFuture[] futures = finder.findPricesStream("iPhone", executor).stream()
        .map(future -> future.thenAccept(s -> System.out
            .println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
        .toArray(size -> new CompletableFuture[size]);
    CompletableFuture.allOf(futures).join();
    System.out.println(
      "All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    ExecutorUtil.shutdownGracefully(executor, 10);
  }

  public static void main(String[] args) {
    int availableProcessors = Runtime.getRuntime().availableProcessors();

    /**
    testFindPrice(availableProcessors);
    testFindPrice(availableProcessors + 1);
    testFindPrice(2 * availableProcessors + 1);
    testFindPrice(10 * availableProcessors);

    testFindPriceWithDiscount(availableProcessors);
    testFindPriceWithDiscount(availableProcessors + 1);
    testFindPriceWithDiscount(2 * availableProcessors + 1);
    **/
    
    testFindPricesStream(availableProcessors);
    testFindPricesStream(availableProcessors + 1);
    testFindPricesStream(2 * availableProcessors + 1);
  }

}
