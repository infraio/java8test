package com.xiaohao.stream;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ResuingStreams {

  public static void main(String[] args) {
    Stream<String> stream = Stream.of("a1", "a2", "b1", "c1", "d1");
    stream.map(s -> s.toUpperCase());
    // stream has already been operated upon or closed
    // stream.filter(s -> s.startsWith("a"));
    // stream.anyMatch(s -> s.startsWith("a"));
    // stream.forEach(s -> System.out.println(s));
    
    Supplier<Stream<String>> streamSupplier = () -> Stream.of("a1", "a2", "b1", "c1", "d1");
    streamSupplier.get().map(s -> s.toUpperCase());
    streamSupplier.get().filter(s -> s.startsWith("a"));
    streamSupplier.get().anyMatch(s -> s.startsWith("a"));
    streamSupplier.get().forEach(s -> System.out.println(s));
  }

}
