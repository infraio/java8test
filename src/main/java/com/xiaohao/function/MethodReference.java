package com.xiaohao.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.xiaohao.Apple;

public class MethodReference {

  public static void main(String[] args) {
    // A method reference to a static method
    List<Integer> list = Arrays.asList("1", "2", "3").stream().map(Integer::parseInt)
        .collect(Collectors.toList());
    // A method reference to an instance method of an arbitrary type
    List<String> List = Arrays.asList("hello", "method", "reference").stream()
        .map(String::toUpperCase).collect(Collectors.toList());
    // A method reference to an instance method of an existing object
    Apple apple = new Apple(100, Apple.Color.RED);
    Supplier<Integer> supplier = apple::getWeight;
    
    // Constructor references
    Supplier<Apple> c1 = Apple::new;
    Apple a1 = c1.get();
    Function<Integer, Apple> c2 = Apple::new;
    Apple a2 = c2.apply(100);
    BiFunction<Integer, Apple.Color, Apple> c3 = Apple::new;
    Apple a3 = c3.apply(100, Apple.Color.RED);
  }

}
