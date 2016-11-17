package com.xiaohao.lambda;

import java.util.function.DoubleFunction;

public class Mathematic {

  public static void main(String[] args) {
    DoubleFunction<Double> f = x -> x + 10;
    double result = integrate(f, 3, 10);
    System.out.println("result is " + result);
    result = integrate(x -> 2 * x + 1, 3, 10);
    System.out.println("result is " + result);
  }

  public static double integrate(DoubleFunction<Double> f, double a, double b) {
    return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;
  }
}
