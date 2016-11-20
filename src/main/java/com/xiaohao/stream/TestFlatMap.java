package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestFlatMap {

  public static void main(String[] args) {
    IntStream.range(1, 4).mapToObj(i -> new Foo("Foo " + i))
        .peek(f -> IntStream.range(1, 4).mapToObj(i -> new Bar(f.name + " -> Bar " + i))
            .forEach(b -> f.bars.add(b)))
        .flatMap(f -> f.bars.stream()).forEach(b -> System.out.println(b.name));

    List<String> words = Arrays.asList("Hello", "FlatMap");
    List<String> uniqueChars = words.stream().map(word -> word.split("")).flatMap(Arrays::stream)
        .distinct().collect(Collectors.toList());
    System.out.println(uniqueChars);

    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = Arrays.asList(4, 5);
    List<int[]> pairs = list1.stream().flatMap(i -> list2.stream().map(j -> new int[] { i, j }))
        .limit(3).collect(Collectors.toList());
    pairs.stream().forEach(array -> {
      Arrays.stream(array).forEach(i -> System.out.print(i + "\t"));
      System.out.println();
    });
  }

}

class Foo {
  String name;
  List<Bar> bars = new ArrayList<>();

  Foo(String name) {
    this.name = name;
  }
}

class Bar {
  String name;

  Bar(String name) {
    this.name = name;
  }
}