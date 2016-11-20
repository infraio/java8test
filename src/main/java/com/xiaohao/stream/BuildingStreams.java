package com.xiaohao.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {

  public static void main(String[] args) {
    Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");

    int[] numbers = new int[] { 1, 2, 3, 4, 5 };
    IntStream intStream = Arrays.stream(numbers);

    try (Stream<String> lines = Files.lines(Paths.get(
      "/home/xiaohao/github/java8test/src/main/java/com/xiaohao/stream/BuildingStreams.java"))) {
      long uniqueWordsCount = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct()
          .count();
      System.out.println("unique words count is " + uniqueWordsCount);
    } catch (IOException e) {
      e.printStackTrace();
    }

    Stream.iterate(0, n -> n + 2).limit(5).forEach(System.out::println);
    Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }).limit(10)
        .map(t -> t[0]).forEach(System.out::println);
    
    IntStream ones = Stream.generate(() -> 1).mapToInt(i -> i);
    IntStream twos = IntStream.generate(() -> 2);
    IntStream threes = IntStream.generate(new IntSupplier() {

      @Override
      public int getAsInt() {
        return 3;
      }
      
    });
    
    IntSupplier fibSupplier = new IntSupplier() {
      int previous = 0;
      int current = 1;
      @Override
      public int getAsInt() {
        int next = previous + current;
        previous = current;
        current = next;
        return current;
      }
    };
    IntStream.generate(fibSupplier).limit(10).forEach(System.out::println);
  }

}
