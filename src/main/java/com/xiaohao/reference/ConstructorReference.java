package com.xiaohao.reference;

public class ConstructorReference {

  public static void main(String[] args) {
    PersonFactory<Person> factory = Person::new;
    Person p = factory.create("zhang", 18);
    System.out.println(p.getClass() + ", name = " + p.getName() + ", age = " + p.getAge());
  }

}

interface PersonFactory<P extends Person> {
  P create(String name, int age);
}