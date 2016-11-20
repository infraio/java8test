package com.xiaohao.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.xiaohao.stream.Dish.Type;

public class MatchingAndFinding {

  public static void main(String[] args) {
    List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
      new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 300, Dish.Type.FISH),
      new Dish("salmon", false, 450, Dish.Type.FISH));

    // anyMath
    if (menu.stream().anyMatch(Dish::isVegetarian)) {
      System.out.println("The menu is (somewhat) vegetarian friendly!!");
    }

    // allMatch, noneMatch
    boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
    isHealthy = menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);

    // findFirst
    Optional<Dish> meatDish = menu.stream().filter(dish -> dish.getType() == Type.MEAT).findFirst();
    meatDish.ifPresent(System.out::println);
    
    // findAny
    Optional<Dish> fishDish = menu.parallelStream().filter(dish -> dish.getType() == Type.FISH).findAny();
    fishDish.ifPresent(System.out::println);
  }

}
