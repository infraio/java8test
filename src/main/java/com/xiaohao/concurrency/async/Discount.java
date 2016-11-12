package com.xiaohao.concurrency.async;

public class Discount {
  public enum Code {
    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
    private final int percentage;

    Code(int percentage) {
      this.percentage = percentage;
    }
  }
  
  public static String applyDiscount(Quote quote) {
    return quote.getShopName() + " price is " + apply(quote.getPrice(), quote.getDiscountCode());
  }
  
  public static String applyDiscountWithRandomDelay(Quote quote) {
    return quote.getShopName() + " price is " + apply(quote.getPrice(), quote.getDiscountCode());
  }
  
  public static double apply(double price, Code code) {
    AsyncUtil.delay();
    return price * (100 - code.percentage) / 100.0;
  }
  
  public static double applyWithRandomDelay(double price, Code code) {
    AsyncUtil.randomDelay();
    return price * (100 - code.percentage) / 100.0;
  }
}
