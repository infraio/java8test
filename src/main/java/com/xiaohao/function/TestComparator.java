package com.xiaohao.function;

import java.util.Comparator;

import com.xiaohao.reference.Person;

public class TestComparator {

  public static void main(String[] args) {
    Comparator<Person> nameComparator = (p1, p2) -> p1.getName().compareTo(p2.getName());
    Comparator<Person> ageComparator = (p1, p2) -> (p1.getAge() - p2.getAge());
    
    Person person1 = new Person("zhang", 18);
    Person person2 = new Person("lei", 20);
    
    System.out.println(nameComparator.compare(person1, person2));
    System.out.println(ageComparator.compare(person1, person2));
  }

}
