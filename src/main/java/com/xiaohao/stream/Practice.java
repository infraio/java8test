package com.xiaohao.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Practice {

  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
      new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
      new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700),
      new Transaction(alan, 2012, 950));

    // Find all transactions in the year 2011 and sort them by value (small to high).
    List<Transaction> result = transactions.stream().filter(t -> t.getYear() == 2011)
        .sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
    System.out.println(result);

    // What are all the unique cities where the traders work?
    List<String> citys = transactions.stream().map(t -> t.getTrader().getCity()).distinct()
        .collect(Collectors.toList());
    System.out.println(citys);

    // Find all traders from Cambridge and sort them by name
    List<Trader> traders = transactions.stream().map(Transaction::getTrader).distinct()
        .filter(t -> t.getCity().equals("Cambridge")).sorted(Comparator.comparing(Trader::getName))
        .collect(Collectors.toList());
    System.out.println(traders);

    // Return a string of all traders’ names sorted alphabetically.
    String names = transactions.stream().map(Transaction::getTrader).distinct().map(Trader::getName)
        .sorted().reduce("", (a, b) -> a + "\t" + b);
    System.out.println(names);

    // Are any traders based in Milan?
    boolean inMilan = transactions.stream().map(Transaction::getTrader)
        .anyMatch(t -> t.getCity().equals("Milan"));
    System.out.println(inMilan);

    // Print all transactions’ values from the traders living in Cambridge
    transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
        .forEach(t -> System.out.println(t.getValue()));

    // What’s the highest value of all the transactions?
    Optional<Integer> highestValue = transactions.stream().map(Transaction::getValue)
        .max(Integer::max);
    highestValue.ifPresent(System.out::println);

    // Find the transaction with the smallest value.
    Optional<Transaction> smallestTransaction = transactions.stream()
        .min(Comparator.comparing(Transaction::getValue));
    smallestTransaction.ifPresent(System.out::println);
  }

}

class Trader {
  private final String name;
  private final String city;

  public Trader(String n, String c) {
    this.name = n;
    this.city = c;
  }

  public String getName() {
    return this.name;
  }

  public String getCity() {
    return this.city;
  }

  public String toString() {
    return "Trader:" + this.name + " in " + this.city;
  }
}

class Transaction {
  private final Trader trader;
  private final int year;
  private final int value;

  public Transaction(Trader trader, int year, int value) {
    this.trader = trader;
    this.year = year;
    this.value = value;
  }

  public Trader getTrader() {
    return this.trader;
  }

  public int getYear() {
    return this.year;
  }

  public int getValue() {
    return this.value;
  }

  public String toString() {
    return "{" + this.trader + ", " + "year: " + this.year + ", " + "value:" + this.value + "}";
  }
}