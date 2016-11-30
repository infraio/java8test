package com.xiaohao.interfaces;

/**
 * 1. Classes always win. A method declaration in the class or a superclass takes priority over any
 * default method declaration.
 * 2. Otherwise, sub-interfaces win: the method with the same signature
 * in the most specific default-providing interface is selected. (If B extends A, B is more specific
 * than A).
 * 3. Finally, if the choice is still ambiguous, the class inheriting from multiple
 * interfaces has to explicitly select which default method implementation to use by overriding it
 * and calling the desired method explicitly.
 */
public class InheritMethods {

  public static void main(String[] args) {
    // Classes win
    new E().hello();
    // sub-interface win
    new F().hello();
  }

}

interface A {
  default void hello() {
    System.out.println("Hello from A");
  }
}

interface B {
  default void hello() {
    System.out.println("Hello from B");
  }
}

interface C extends A {
  default void hello() {
    System.out.println("Hello from C");
  }
}

class D implements A {
  public void hello() {
    System.out.println("Hello from D");
  }
}

class E extends D implements A, B {

}

class F implements A, C {

}

/**
 * Compile error : Duplicate default methods named hello with the parameters () and () are inherited
 * from the types B and A
 */
/**
 * class G implements A,B { }
 */