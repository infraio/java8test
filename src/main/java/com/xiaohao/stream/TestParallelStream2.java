package com.xiaohao.stream;

import java.util.Arrays;
import java.util.List;

import com.xiaohao.Person;

public class TestParallelStream2 {

  public static void main(String[] args) {
    Arrays.asList("a1", "a2", "b1", "c2", "c1").parallelStream().filter(s -> {
      System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
      return true;
    }).map(s -> {
      System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
      return s.toUpperCase();
    }).forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));

    System.out.println("\n\n");
    Arrays.asList("a1", "a2", "b1", "c2", "c1").parallelStream().filter(s -> {
      System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
      return true;
    }).map(s -> {
      System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
      return s.toUpperCase();
    }).sorted((s1, s2) -> {
      System.out.format("sort: %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
      return s1.compareTo(s2);
    }).forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));

    System.out.println("\n\n");
    List<Person> persons = Arrays.asList(new Person("Max", 18), new Person("Peter", 23),
      new Person("Pamela", 23), new Person("David", 12));
    Integer ageSum = persons.parallelStream().reduce(0, (sum, p) -> {
      System.out.format("accumulator: sum=%s; person=%s [%s]\n", sum, p,
        Thread.currentThread().getName());
      return sum += p.getAge();
    } , (sum1, sum2) -> {
      System.out.format("combiner: sum1=%s; sum2=%s [%s]\n", sum1, sum2,
        Thread.currentThread().getName());
      return sum1 + sum2;
    });
    System.out.println(ageSum);
  }

}
