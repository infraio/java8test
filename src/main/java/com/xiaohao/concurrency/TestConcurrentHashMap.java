package com.xiaohao.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class TestConcurrentHashMap {

  public static void main(String[] args) {
    ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
    IntStream.range(0, 10).forEach(i -> map.computeIfAbsent(i, k -> "String " + k));

    System.out.println("Test forEach");
    map.forEach(1, (key, value) -> System.out.println(
      "Thread " + Thread.currentThread().getName() + ", key " + key + ", value " + value));

    System.out.println("\nTest search");
    String result = map.search(1, (key, value) -> {
      System.out.println(
        "Thread " + Thread.currentThread().getName() + ", key " + key + ", value " + value);
      return key.equals(8) ? value : null;
    });
    System.out.println("Search result is " + result);
    
    System.out.println("\nTest search");
    result = map.search(1, (key, value) -> {
      System.out.println(
        "Thread " + Thread.currentThread().getName() + ", key " + key + ", value " + value);
      return key.equals(2) ? value : null;
    });
    System.out.println("Search result is " + result);
    
    System.out.println("\nTest reduce");
    result = map.reduce(1, (key, value) -> {
      System.out.println("Transform Thread " + Thread.currentThread().getName());
      return key + "=" + value;
    }, (s1, s2) -> {
      System.out.println("Reduce Thread " + Thread.currentThread().getName());
      return s1 + " , " + s2; 
    });
    System.out.println(result);
  }

}
