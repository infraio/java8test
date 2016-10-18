package com.xiaohao.reference;

import com.xiaohao.interfaces.Converter;
public class MethodReference {

  public static void main(String[] args) {
    // references to class static methods
    Converter<String, Integer> converter = Integer::valueOf;
    Integer converted = converter.convert("123");
    System.out.println(converted);
    
    // references to object methods
    Something something = new Something();
    Converter<String, String> converter2 = something::startsWith;
    System.out.println(converter2.convert("Java"));
  }

}

class Something {
  String startsWith(String s) {
    return String.valueOf(s.charAt(0));
  }
}
