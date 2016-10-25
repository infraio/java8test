package com.xiaohao.stream;

import java.util.stream.Stream;

public class TestProcessingOrder {

  public static void main(String[] args) {
    /**
     * After a1 passed both filter and forEach method, then a2 start to process.
     */
    Stream.of("a1", "a2", "b1", "c1", "d1").filter(s -> {
      System.out.println("filter " + s);
      return true;
    }).forEach(s -> System.out.println("forEach " + s));
    
    // Reduce the actual number of operations performed on each element
    // After anyMatch return true, no need to perform operation on c1 and d1
    Stream.of("a1", "a2", "b1", "c1", "d1").map(s -> {
      System.out.println("map " + s);
      return s.toUpperCase();
    }).anyMatch(s -> {
      System.out.println("anyMatch " + s);
      return s.startsWith("B");
    });
    
    // First map, then filter
    System.out.println("First map, then filter");
    Stream.of("a1", "a2", "b1", "c1", "d1").map(s -> {
      System.out.println("map " + s);
      return s.toUpperCase();
    }).filter(s -> {
      System.out.println("filter " + s);
      return s.startsWith("B");
    }).forEach(s -> System.out.println("forEach " + s));
    
    // First filter, then map
    System.out.println("First filter, then map");
    Stream.of("a1", "a2", "b1", "c1", "d1").filter(s -> {
      System.out.println("filter " + s);
      return s.startsWith("b");
    }).map(s -> {
      System.out.println("map " + s);
      return s.toUpperCase();
    }).forEach(s -> System.out.println("forEach " + s));
    
    // sorted -> filter -> map -> forEach
    System.out.println("sorted -> filter -> map -> forEach");
    Stream.of("c1", "a2", "b1", "a1", "d1").sorted((s1, s2) -> {
      System.out.println("sorted " + s1 + " " + s2);
      return s1.compareTo(s2);
    }).filter(s -> {
      System.out.println("filter " + s);
      return s.startsWith("b");
    }).map(s -> {
      System.out.println("map " + s);
      return s.toUpperCase();
    }).forEach(s -> System.out.println("forEach " + s));
    
    // filter -> sorted -> map -> forEach
    System.out.println("filter -> sorted -> map -> forEach");
    Stream.of("c1", "a2", "b1", "a1", "d1").filter(s -> {
      System.out.println("filter " + s);
      return s.startsWith("b");
    }).sorted((s1, s2) -> {
      System.out.println("sorted " + s1 + " " + s2);
      return s1.compareTo(s2);
    }).map(s -> {
      System.out.println("map " + s);
      return s.toUpperCase();
    }).forEach(s -> System.out.println("forEach " + s));
  }

}
