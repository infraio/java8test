package com.xiaohao.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import com.xiaohao.Apple;

public class ComposeLambda {

  public static void main(String[] args) {
    List<Apple> inventory = Arrays.asList(new Apple(100, Apple.Color.RED),
      new Apple(150, Apple.Color.GREEN), new Apple(200, Apple.Color.YELLOW),
      new Apple(150, Apple.Color.RED));
    inventory
        .sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(a -> a.getColor()));
    inventory.stream().forEach(System.out::println);

    Predicate<Apple> redApple = a -> a.getColor() == Apple.Color.RED;
    Predicate<Apple> notRedApple = redApple.negate();
    Predicate<Apple> redAndBigApple = redApple.and(a -> a.getWeight() >= 150);
    Predicate<Apple> notRedOrSmallApple = notRedApple.or(a -> a.getWeight() <= 100);

    Function<Integer, Integer> f = x -> x + 1;
    Function<Integer, Integer> g = x -> x * 2;
    Function<Integer, Integer> h = f.andThen(g);
    IntStream.range(0, 10)
        .forEach(i -> System.out.println("f(g(x)) = " + h.apply(i) + " when x = " + i));
    IntStream.range(0, 10)
        .forEach(i -> System.out.println("g(f(x)) = " + f.compose(g).apply(i) + " when x = " + i));
  }

}
