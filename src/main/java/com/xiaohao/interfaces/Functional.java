package com.xiaohao.interfaces;

public class Functional {

  public static void main(String[] args) {
    Converter<String, Integer> converter = s -> Integer.valueOf(s);
    System.out.println(converter.convert("123"));
  }

}

/**
 * A so called functional interface must contain exactly one abstract method declaration.
 * Each lambda expression of that type will be matched to this abstract method.
 * Since default methods are not abstract you're free to add default methods
 * to your functional interface.
 */
@FunctionalInterface
interface Converter<F, T> {
  T convert(F f);
}