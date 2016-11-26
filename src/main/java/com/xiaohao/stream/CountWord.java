package com.xiaohao.stream;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CountWord {

  public static final String DESCRIPTION = "hello stream! Implementing your own Spliterator! "
      + "The Spliterator is another new interface added to Java 8; "
      + "its name stands for splitable iterator. "
      + "Like Iterators, Spliterators are used to traverse the elements of a source, "
      + "but they’re also designed to do this in parallel. "
      + "Although you may not have to develop your own Spliterator in practice, "
      + "understanding how to do so will give you a wider understanding about how parallel streams work. "
      + "Java 8 already provides a default Spliterator implementation for all the data structures included in its Collections Framework.";

  public static void main(String[] args) {
    int count = countWordsIteratively(DESCRIPTION);
    System.out.println("Words number is " + count);
    count = countWordsByStream(DESCRIPTION);
    System.out.println("Words number is " + count);
    count = countWordsByParallelStream(DESCRIPTION);
    System.out.println("Words number is " + count);
  }

  public static int countWordsIteratively(String s) {
    int counter = 0;
    boolean lastSpace = true;
    for (int i = 0; i < s.length(); i++) {
      if (Character.isWhitespace(s.charAt(i))) {
        lastSpace = true;
      } else {
        if (lastSpace) {
          counter++;
        }
        lastSpace = false;
      }
    }
    return counter;
  }

  public static int countWordsByStream(String s) {
    Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(i -> s.charAt(i));
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate,
      WordCounter::combine);
    return wordCounter.getCounter();
  }

  public static int countWordsByParallelStream(String s) {
    Spliterator<Character> spliterator = new WordCounterSpliterator(s);
    // true means parallel
    Stream<Character> stream = StreamSupport.stream(spliterator, true);
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
      WordCounter::accumulate, WordCounter::combine);
    return wordCounter.getCounter();
  }
}

class WordCounter {
  int counter = 0;
  boolean lastSpace = true;

  public WordCounter(int counter, boolean lastSpace) {
    this.counter = counter;
    this.lastSpace = lastSpace;
  }

  public WordCounter accumulate(Character ch) {
    if (Character.isWhitespace(ch)) {
      return lastSpace ? this : new WordCounter(this.counter, true);
    } else {
      return lastSpace ? new WordCounter(this.counter + 1, false) : this;
    }
  }

  public WordCounter combine(WordCounter other) {
    return new WordCounter(this.counter + other.counter, this.lastSpace);
  }

  public int getCounter() {
    return this.counter;
  }
}
