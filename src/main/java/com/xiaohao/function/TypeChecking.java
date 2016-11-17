package com.xiaohao.function;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

import com.xiaohao.Apple;

public class TypeChecking {

  public static void main(String[] args) {
    /**
     * Lambda expressions can get their target type from an assignment context, method invocation
     * context (parameters and return), and a cast context.
     */
    Callable<Integer> c = () -> 42;
    PrivilegedAction<Integer> pa = () -> 42;

    Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.compareTo(a2);
    ToIntBiFunction<Apple, Apple> c2 = (a1, a2) -> a1.compareTo(a2);
    // Apple type can be omitted in the lambda syntax
    BiFunction<Apple, Apple, Integer> c3 = (a1, a2) -> a1.compareTo(a2);

    List<String> list = new ArrayList<>();
    // Predicate has a boolean return
    Predicate<String> p = s -> list.add(s);
    // Consumer has a void return, but list.add(s) return a boolean value
    Consumer<String> b = s -> list.add(s);
  }

}
