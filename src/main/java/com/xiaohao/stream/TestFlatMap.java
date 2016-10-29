package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TestFlatMap {

  public static void main(String[] args) {
    IntStream.range(1, 4)
      .mapToObj(i -> new Foo("Foo " + i))
      .peek(f -> IntStream.range(1, 4).mapToObj(i -> new Bar(f.name + " -> Bar " + i)).forEach(b -> f.bars.add(b)))
      .flatMap(f -> f.bars.stream())
      .forEach(b -> System.out.println(b.name));
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