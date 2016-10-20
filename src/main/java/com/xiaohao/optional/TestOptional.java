package com.xiaohao.optional;

import java.util.Optional;

public class TestOptional {

  public static void main(String[] args) {
    Optional<String> valueOptional = Optional.of("zhang");
    Optional<String> emptyOptional = Optional.empty();
    
    System.out.println(valueOptional.get());
    System.out.println(valueOptional.isPresent());
    System.out.println(emptyOptional.isPresent());
    System.out.println(valueOptional.orElse("lei"));
    System.out.println(emptyOptional.orElse("lei"));
  }

}
