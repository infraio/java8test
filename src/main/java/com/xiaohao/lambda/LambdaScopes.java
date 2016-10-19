package com.xiaohao.lambda;

import com.xiaohao.interfaces.Converter;

public class LambdaScopes {

  public static void main(String[] args) {
    // Access local variables which must be implicitly final
    // final int num = 3;
    int num = 3;
    Converter<Integer, Integer> converter = from -> (from + num);
    System.out.println(converter.convert(2));

    // error: Local variable num defined in an enclosing scope must be final or effectively final
    // num = 5;

    // Access class field and static variable
    TestScopes testScopes = new TestScopes(3, 5);
    System.out.println(testScopes.convertByField(2));
    System.out.println(testScopes.convertByStaticVariable(5));
  }

}

class TestScopes {
  int num;
  static int staticNum;

  public TestScopes(int num, int staticNum) {
    this.num = num;
    this.staticNum = staticNum;
  }

  int convertByField(int i) {
    Converter<Integer, Integer> converter = from -> (from + num);
    return converter.convert(i);
  }

  int convertByStaticVariable(int i) {
    Converter<Integer, Integer> converter = from -> (from + staticNum);
    return converter.convert(i);
  }
}