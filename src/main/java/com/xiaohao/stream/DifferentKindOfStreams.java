package com.xiaohao.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.DoubleStream;

public class DifferentKindOfStreams {

  public static void main(String[] args) {
    IntStream.range(0, 3).forEach(System.out::println);

    Arrays.stream(new int[] { 1, 2, 3 }).map(n -> 2 * n + 1).average()
        .ifPresent(n -> System.out.println(n));

    Stream.of("a1", "a2", "a3").map(s -> s.substring(1)).mapToInt(Integer::parseInt).max()
        .ifPresent(n -> System.out.println(n));

    IntStream.range(0, 3).mapToObj(i -> "a" + i).forEach(System.out::println);

    Stream.of(1.0, 2.0, 3.0).mapToInt(Double::intValue).forEach(n -> System.out.println(n));

    DoubleStream.of(1.0, 2.0, 3.0).filter(n -> (n > 1.5)).min().ifPresent(System.out::println);
  }

}
