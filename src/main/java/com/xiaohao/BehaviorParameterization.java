package com.xiaohao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BehaviorParameterization {

  public static void main(String[] args) {
    List<Apple> inventory = Arrays.asList(new Apple(100, Apple.Color.RED),
      new Apple(150, Apple.Color.GREEN), new Apple(200, Apple.Color.YELLOW),
      new Apple(150, Apple.Color.RED));

    System.out.println("Find red apples:");
    List<Apple> redApples = filter(inventory,
      (Apple apple) -> apple.getColor().equals(Apple.Color.RED));
    redApples.stream().forEach(System.out::println);

    System.out.println("Find bigger apples");
    filter(inventory, (Apple apple) -> apple.getWeight() >= 150).stream()
        .forEach(System.out::println);

    System.out.println("Find bigger red apples");
    filter(inventory, apple -> apple.getWeight() >= 150 && apple.getColor().equals(Apple.Color.RED))
        .stream().forEach(System.out::println);

    System.out.println("Find odd numbers");
    List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    filter(nums, num -> num % 2 == 1).stream().forEach(System.out::println);
  }

  private static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for (T item : list) {
      if (p.test(item)) {
        result.add(item);
      }
    }
    return result;
  }
}

@FunctionalInterface
interface Predicate<T> {
  boolean test(T t);
}
