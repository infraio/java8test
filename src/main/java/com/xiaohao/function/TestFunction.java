package com.xiaohao.function;

import java.util.function.Function;

public class TestFunction {

  public static void main(String[] args) {
    Function<String, Integer> toInteger = s -> Integer.valueOf(s);
    Integer i = toInteger.apply("123");
    System.out.println(i);

    Function<String, String> backToString = toInteger.andThen(n -> String.valueOf(n));
    String str = backToString.apply("456");
    System.out.println(backToString.apply(str));

    Function<String, Integer> getVersion =
        toInteger.compose(s -> String.valueOf(s.charAt(s.length() - 1)));
    Integer version = getVersion.apply("java8");
    System.out.println(version);
  }

}
