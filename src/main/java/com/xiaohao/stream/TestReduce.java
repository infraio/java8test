package com.xiaohao.stream;

import java.util.Arrays;
import java.util.List;

import com.xiaohao.Person;

public class TestReduce {

  public static void main(String[] args) {
    List<Person> persons = Arrays.asList(new Person("Max", 18), new Person("Peter", 23),
      new Person("Pamela", 23), new Person("David", 12));

    persons.stream().reduce((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2)
        .ifPresent(System.out::println);

    Person result = persons.stream().reduce(new Person("", 0), (p1, p2) -> {
      p1.setAge(p1.getAge() + p2.getAge());
      return p1;
    });
    System.out.println("Sum age is " + result.getAge());

    // Combine operation isn't needed when executed sequentially
    Integer ageSum = persons.stream().reduce(0, (sum, p) -> {
      System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
      return sum += p.getAge();
    } , (sum1, sum2) -> {
      System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
      return sum1 + sum2;
    });
    System.out.println(ageSum);
    
    System.out.println("parallel stream");
    ageSum = persons.parallelStream().reduce(0, (sum, p) -> {
      System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
      return sum += p.getAge();
    } , (sum1, sum2) -> {
      System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
      return sum1 + sum2;
    });
    System.out.println(ageSum);
  }

}
