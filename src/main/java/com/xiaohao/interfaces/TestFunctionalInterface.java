package com.xiaohao.interfaces;

public class TestFunctionalInterface {

  public static void main(String[] args) {
    Runnable r1 = () -> System.out.println("Hello FunctionalInterface 1");
    
    Runnable r2 = new Runnable() {

      @Override
      public void run() {
        System.out.println("Hello Anonymous class");
      }
      
    };
    
    process(r1);
    process(r2);
    process(() -> System.out.println("Hello FuncationalInterface 2"));
  }
  
  public static void process(Runnable runnable) {
    runnable.run();
  }

}
