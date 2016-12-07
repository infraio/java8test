package com.xiaohao.stream;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class LazyEvaluation {

  public static void main(String[] args) {
    MyList<Integer> numbers = from(1);
    System.out.println(numbers.head());
    MyList<Integer> iter = numbers.tail();
    for (int i = 1; i < 10; i++) {
      System.out.println(iter.head());
      iter = iter.tail();
    }

    numbers = primes(from(2));
    System.out.println(numbers.head());
    iter = numbers.tail();
    for (int i = 1; i < 10; i++) {
      System.out.println(iter.head());
      iter = iter.tail();
    }
  }

  public static MyList<Integer> primes(MyList<Integer> numbers) {
    return new LazyList<>(numbers.head(),
        () -> primes(numbers.tail().filter(n -> n % numbers.head() != 0)));
  }

  public static LazyList<Integer> from(int n) {
    return new LazyList<Integer>(n, () -> from(n + 1));
  }
}

interface MyList<T> {
  T head();

  MyList<T> tail();

  MyList<T> filter(Predicate<T> p);

  default boolean isEmpty() {
    return true;
  }
}

class LazyList<T> implements MyList<T> {

  final T head;
  final Supplier<MyList<T>> tailSupplier;

  public LazyList(T head, Supplier<MyList<T>> tailSupplier) {
    this.head = head;
    this.tailSupplier = tailSupplier;
  }

  @Override
  public T head() {
    return head;
  }

  @Override
  public MyList<T> tail() {
    return tailSupplier.get();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public MyList<T> filter(Predicate<T> p) {
    return isEmpty() ? this
        : p.test(head) ? new LazyList<T>(head, () -> tail().filter(p)) : tail().filter(p);
  }
}