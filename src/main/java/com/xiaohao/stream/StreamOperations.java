package com.xiaohao.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOperations {

  public static void main(String[] args) {
    List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
      new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 300, Dish.Type.FISH),
      new Dish("salmon", false, 450, Dish.Type.FISH));

    /**
     * 1. filter, map, limit are intermediate operations, count is terminal operation 
     * 2. limit operation and a technique called short-circuiting
     * 3. filter and map were merged into the same pass (we call this technique loop fusion).
     */
    List<String> names = menu.stream().filter(dish -> {
      System.out.println("filtering " + dish);
      return dish.getCalories() > 500;
    }).map(dish -> {
      System.out.println("mapping " + dish);
      return dish.getName();
    }).limit(3).collect(Collectors.toList());
    System.out.println(names);
  }

}
