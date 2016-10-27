package com.xiaohao.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.xiaohao.Person;

import java.util.Map;
import java.util.StringJoiner;

public class TestCollect {

  public static void main(String[] args) {
    List<Person> persons = Arrays.asList(new Person("Max", 18), new Person("Peter", 23),
      new Person("Pamela", 23), new Person("David", 12));

    List<Person> filtered = persons.stream().filter(p -> p.getName().startsWith("P"))
        .collect(Collectors.toList());
    System.out.println(filtered);

    Map<Integer, List<Person>> personsByAge = persons.stream()
        .collect(Collectors.groupingBy(p -> p.getAge()));
    personsByAge.forEach((age, pList) -> System.out.println("age: " + age + ", " + pList));

    Double averageAge = persons.stream().collect(Collectors.averagingInt(p -> p.getAge()));
    System.out.println(averageAge);

    IntSummaryStatistics ageSummary = persons.stream()
        .collect(Collectors.summarizingInt(p -> p.getAge()));
    System.out.println(ageSummary);

    String str = persons.stream().filter(p -> p.getAge() > 18).map(p -> p.getName())
        .collect(Collectors.joining(" and ", "Person ", " are older than 18."));
    System.out.println(str);

    Map<Integer, String> map = persons.stream().collect(
      Collectors.toMap(p -> p.getAge(), p -> p.getName(), (name1, name2) -> name1 + ";" + name2));
    System.out.println(map);

    Collector<Person, StringJoiner, String> personNameCollector = Collector.of(
      () -> new StringJoiner(" | "), // supplier
      (j, p) -> j.add(p.getName().toUpperCase()), // accumulator
      (j1, j2) -> j1.merge(j2), // combiner
      StringJoiner::toString); // finisher
    String names = persons.stream().collect(personNameCollector);
    System.out.println(names);
  }

}
