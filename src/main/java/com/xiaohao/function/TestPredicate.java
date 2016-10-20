package com.xiaohao.function;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class TestPredicate {

  public static void main(String[] args) {
    Predicate<String> predicate = s -> (s.length() > 0);
    System.out.println(predicate.test("java"));
    System.out.println(predicate.negate().test("java"));

    Predicate<String> isEmpty = String::isEmpty;
    Predicate<String> notEmpty = isEmpty.negate();
    System.out.println(isEmpty.test("java"));
    System.out.println(notEmpty.test("java"));

    Predicate<List<Integer>> isNull = Objects::isNull;
    Predicate<List<Integer>> notNull = Objects::nonNull;
    List<Integer> list1 = null;
    List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
    System.out.println(isNull.test(list1));
    System.out.println(isNull.test(list2));
    System.out.println(notNull.test(list1));
    System.out.println(notNull.test(list2));
  }

}
