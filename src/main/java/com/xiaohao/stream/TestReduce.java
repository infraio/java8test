package com.xiaohao.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
    sum.ifPresent(i -> System.out.println("Sum is " + i));
    Optional<Integer> max = numbers.stream().reduce(Integer::max);
    max.ifPresent(i -> System.out.println("Max is " + i));
    max = numbers.stream().max(Integer::max);
    max.ifPresent(i -> System.out.println("Max is " + i));
    Optional<Integer> min = numbers.stream().reduce(Integer::min);
    min.ifPresent(i -> System.out.println("Min is " + i));
    min = numbers.stream().min(Integer::min);
    min.ifPresent(i -> System.out.println("Min is " + i));
  }

}
