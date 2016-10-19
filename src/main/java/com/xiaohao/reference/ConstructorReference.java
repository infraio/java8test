package com.xiaohao.reference;

public class ConstructorReference {

  public static void main(String[] args) {
    PersonFactory<Person> factory = Person::new;
    Person p = factory.create("zhang", 18);
    System.out.println(p.getClass() + ", name = " + p.getName() + ", age = " + p.getAge());
  }

}

class Person {
  private String name;
  private int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return this.name;
  }

  public int getAge() {
    return this.age;
  }
}

interface PersonFactory<P extends Person> {
  P create(String name, int age);
}