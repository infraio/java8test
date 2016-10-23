package com.xiaohao;

import java.util.HashMap;
import java.util.Map;

public class TestMap {

  public static void main(String[] args) {
    Map<Integer, String> map1 = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      map1.putIfAbsent(i, "value" + i);
    }
    map1.forEach((key, value) -> System.out.println("key=" + key + ", value=" + value));
    
    Map<Integer, String> map2 = new HashMap<>();
    for (int i = 0; i < 10; i++) {
      map2.computeIfAbsent(i, key -> ("value" + key));
    }
    map2.forEach((key, value) -> System.out.println("key=" + key + ", value=" + value));
    
    System.out.println(map2.getOrDefault(1, "not found"));
    
    for (int i = 0; i < 20; i++) {
      map2.merge(i, "value" + i, (value, newValue) -> (value + "merge"));
    }
    map2.forEach((key, value) -> System.out.println("key=" + key + ", value=" + value));
  }

}
