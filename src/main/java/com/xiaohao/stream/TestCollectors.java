package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.xiaohao.stream.Dish.Type;

public class TestCollectors {

  public static int SIZE = 10_000_000;
  public static void main(String[] args) {
    List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
      new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 300, Dish.Type.FISH),
      new Dish("salmon", false, 450, Dish.Type.FISH));

    // Finding maximum and minimum in a stream of values
    Optional<Dish> mostCalorieDish = menu.stream()
        .collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));

    // Summarization
    int sumCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
    double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
    IntSummaryStatistics menuStatistics = menu.stream()
        .collect(Collectors.summarizingInt(Dish::getCalories));
    System.out.println(menuStatistics);

    // joining
    String names = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
    System.out.println(names);

    // reduce
    sumCalories = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
    mostCalorieDish = menu.stream()
        .collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

    // Grouping
    Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
        .collect(Collectors.groupingBy(Dish::getType));
    System.out.println(dishesByType);
    Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
        .collect(Collectors.groupingBy((Dish dish) -> {
          if (dish.getCalories() < 400) {
            return CaloricLevel.DIET;
          } else if (dish.getCalories() < 700) {
            return CaloricLevel.NORMAL;
          } else {
            return CaloricLevel.FAT;
          }
        }));
    System.out.println(dishesByCaloricLevel);

    // Multi level grouping
    Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeAndCaloricLevel = menu.stream()
        .collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy((Dish dish) -> {
          if (dish.getCalories() < 400) {
            return CaloricLevel.DIET;
          } else if (dish.getCalories() < 700) {
            return CaloricLevel.NORMAL;
          } else {
            return CaloricLevel.FAT;
          }
        })));
    System.out.println(dishesByTypeAndCaloricLevel);

    Map<Dish.Type, Long> typesCount = menu.stream()
        .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
    System.out.println(typesCount);
    Map<Dish.Type, Dish> mostCaloriesDishByType = menu.stream()
        .collect(Collectors.groupingBy(Dish::getType, Collectors.collectingAndThen(
          Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
    System.out.println(mostCaloriesDishByType);

    // Partition
    Map<Boolean, List<Dish>> partitionedMenu = menu.stream()
        .collect(Collectors.partitioningBy(Dish::isVegetarian));
    System.out.println(partitionedMenu);
    Map<Boolean, Map<Type, List<Dish>>> partitionedMenuByType = menu.stream().collect(
      Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
    System.out.println(partitionedMenuByType);

    Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream()
        .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.collectingAndThen(
          Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

    long startTime = System.currentTimeMillis();
    Map<Boolean, List<Integer>> partitionPrimes = IntStream.range(2, SIZE).boxed()
        .collect(Collectors.partitioningBy(n -> isPrime(n)));
    System.out.println("Take " + (System.currentTimeMillis() - startTime) + " ms");

    startTime = System.currentTimeMillis();
    partitionPrimes = partitionPrimesWithCustomCollector(SIZE);
    System.out.println("Take " + (System.currentTimeMillis() - startTime) + " ms");
  }

  public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
    return IntStream.range(2, n).boxed().collect(() -> new HashMap<Boolean, List<Integer>>() {
      {
        put(true, new ArrayList<Integer>());
        put(false, new ArrayList<Integer>());
      }
    }, (map, candidate) -> {
      map.get(isPrime(map.get(true), candidate)).add(candidate);
    } , (map1, map2) -> {
      map1.get(true).addAll(map2.get(true));
      map2.get(false).addAll(map2.get(false));
    });
  }

  public static boolean isPrime(int candidate) {
    int candidateRoot = (int) Math.sqrt((double) candidate);
    return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
  }

  public static boolean isPrime(List<Integer> primes, int candidate) {
    int candidateRoot = (int) Math.sqrt((double) candidate);
    return takeWhile(primes, prime -> prime < candidateRoot).stream()
        .noneMatch(prime -> candidate % prime == 0);
  }

  public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
    for (int i = 0; i < list.size(); i++) {
      if (!p.test(list.get(i))) {
        return list.subList(0, i);
      }
    }
    return list;
  }

  public enum CaloricLevel {
    DIET, NORMAL, FAT
  }
}
