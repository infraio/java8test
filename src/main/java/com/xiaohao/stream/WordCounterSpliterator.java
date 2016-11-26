package com.xiaohao.stream;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {

  private final String s;
  private int curPos = 0;

  public WordCounterSpliterator(String s) {
    this.s = s;
  }

  @Override
  public boolean tryAdvance(Consumer<? super Character> action) {
    action.accept(s.charAt(curPos++));
    return curPos < s.length();
  }

  @Override
  public Spliterator<Character> trySplit() {
    int curSize = s.length() - curPos;
    if (curSize < 10) {
      return null;
    }
    for (int i = curPos + curSize / 3; i < s.length(); i++) {
      if (Character.isWhitespace(s.charAt(curPos))) {
        String subStr = s.substring(curPos, i);
        curPos = i;
        return new WordCounterSpliterator(subStr);
      }
    }
    return null;
  }

  @Override
  public long estimateSize() {
    return s.length() - curPos;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
  }

}
