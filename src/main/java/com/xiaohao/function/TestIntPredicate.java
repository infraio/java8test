package com.xiaohao.function;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class TestIntPredicate {

  public static void main(String[] args) {
    Predicate<Integer> oddPredicate = (Integer i) -> i % 2 == 1;
    // auto boxing
    Arrays.asList(1, 2, 3, 4, 5)
        .forEach(i -> System.out.println(i + " is odd ? " + oddPredicate.test(i)));

    IntPredicate evenPredicate = (int i) -> i % 2 == 0;
    // avoid boxing
    Arrays.asList(1, 2, 3, 4, 5)
        .forEach(i -> System.out.println(i + " is even ? " + evenPredicate.test(i)));
  }

}
