package com.xiaohao.lambda;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.List;

import com.xiaohao.Apple;

public class DebugLambda {

  public static void main(String[] args) {
    IntStream.range(1, 3).map(i -> 10 / i).forEach(System.out::println);

    List<Apple> apples = Arrays.asList(new Apple(100), new Apple(200), null);
    apples.stream().map(a -> a.getWeight()).forEach(System.out::println);
  }

}
