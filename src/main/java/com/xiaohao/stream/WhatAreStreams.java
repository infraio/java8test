package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WhatAreStreams {

  public static void main(String[] args) {
    List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
      new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 300, Dish.Type.FISH),
      new Dish("salmon", false, 450, Dish.Type.FISH));

    // Code in Java 7
    List<Dish> lowerCaloriesDishes = new ArrayList<>();
    for (Dish dish : menu) {
      if (dish.getCalories() <= 400) {
        lowerCaloriesDishes.add(dish);
      }
    }

    Collections.sort(lowerCaloriesDishes, new Comparator<Dish>() {

      @Override
      public int compare(Dish o1, Dish o2) {
        return o1.getName().compareTo(o2.getName());
      }

    });

    List<String> lowerCaloriesDishesNames = new ArrayList<>();
    for (Dish dish : lowerCaloriesDishes) {
      lowerCaloriesDishesNames.add(dish.getName());
    }

    for (String name : lowerCaloriesDishesNames) {
      System.out.println(name);
    }
    System.out.println();

    // Code in Java 8
    menu.stream().filter(dish -> dish.getCalories() <= 400)
        .sorted((d1, d2) -> d1.getName().compareTo(d2.getName())).map(dish -> dish.getName())
        .forEach(System.out::println);
  }

}
