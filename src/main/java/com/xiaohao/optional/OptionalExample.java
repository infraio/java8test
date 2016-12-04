package com.xiaohao.optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalExample {

  public static void main(String[] args) {
    Configuration conf = new Configuration();
    conf.set("a", "hello");
    conf.set("b", "1000");
    conf.set("c", "true");
    conf.set("d", "-1");

    System.out.println(conf.getInt("a"));
    System.out.println(conf.getInt("b"));
    System.out.println(conf.getInt("c"));
    System.out.println(conf.getInt("d"));
  }

}

class Configuration {
  private final Map<String, String> confsMap;

  public Configuration() {
    this.confsMap = new HashMap<>();
  }

  /**
   * Only return positive number or 0.
   * @param key
   */
  public int getInt(String key) {
    return Optional.ofNullable(confsMap.get(key)).flatMap(OptionalUtility::stringToInt)
        .filter(i -> i > 0).orElse(0);
  }

  public void set(String key, String value) {
    confsMap.put(key, value);
  }
}
