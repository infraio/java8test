package com.xiaohao.interfaces;

import java.util.HashMap;
import java.util.Map;

public class DefaultMethods {

  public static void main(String[] args) {
    TableInterface table1 = new Table();
    table1.put("key1", "value1");
    System.out.println(table1.get("key1"));
    
    TableInterface table2 = new MockTable();
    table2.put("key1", "value1");
    System.out.println(table2.get("key1"));
  }

}

interface TableInterface {
  default String get(String key) {
    return "default";
  }
  void put(String key, String value);
}

class Table implements TableInterface {

  private Map<String, String> map;
  
  public Table() {
    map = new HashMap<>();
  }
  
  @Override
  public void put(String key, String value) {
    map.put(key, value);
  }
  
  @Override
  public String get(String key) {
    return map.get(key);
  }
}

class MockTable implements TableInterface {

  @Override
  public void put(String key, String value) {
  }
  
}