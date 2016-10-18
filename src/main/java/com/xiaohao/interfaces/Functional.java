package com.xiaohao.interfaces;

public class Functional {

  public static void main(String[] args) {
    Converter<String, Integer> converter = s -> Integer.valueOf(s);
    System.out.println(converter.convert("123"));
  }

}
