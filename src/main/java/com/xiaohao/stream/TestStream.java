package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TestStream {
  
  private static final int SIZE = 10;
  private static final int BOUND = 1000;

	public static void main(String[] args) {
	  List<Integer> list = new ArrayList<>();
	  Random random = new Random();
	  for (int i = 0; i < SIZE; i++) {
	    list.add(random.nextInt(BOUND));
	  }
	  System.out.println(list);
	  
	  // filter
	  System.out.println("Test filter");
	  list.stream().filter(n -> (n > BOUND / 2)).forEach(System.out::println);
	  
	  // sorted
	  System.out.println("Test sorted");
	  list.stream().sorted().filter(n -> (n > BOUND / 2)).forEach(n -> System.out.println(n));
	  
	  // map
	  System.out.println("Test map");
	  list.stream().map(n -> (n > BOUND / 2)).forEach(System.out::println);
	  
	  // match
	  System.out.println("Test match");
	  System.out.println(list.stream().allMatch(n -> (n > BOUND / 2)));
	  System.out.println(list.stream().anyMatch(n -> (n > BOUND / 2)));
	  System.out.println(list.stream().noneMatch(n -> (n > BOUND / 2)));
	  
	  // count
	  System.out.println("Test count");
	  System.out.println(list.stream().filter(n -> (n > BOUND / 2)).count());
	  
	  // reduce
	  System.out.println("Test reduce");
	  Optional<Integer> result = list.stream().reduce((n1, n2) -> (n1 + n2));
	  result.ifPresent(n -> System.out.println(n));
	}

}
