package com.xiaohao.concurrency.async;

public class ExchangeService {
  enum Money {
    USD(1.0), EUR(0.8), CNY(6.8);
    
    double rate;
    
    Money (double rate) {
      this.rate = rate;
    }
  }
  
  public static double getRate(Money m1, Money m2) {
    return m1.rate / m2.rate;
  }
  
}
