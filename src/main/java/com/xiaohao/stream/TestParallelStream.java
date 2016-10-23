package com.xiaohao.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class TestParallelStream {

  private static final int SIZE = 1000000;
  public static void main(String[] args) {
    List<String> values = new ArrayList<>(SIZE);
    for (int i = 0; i < SIZE; i++) {
        UUID uuid = UUID.randomUUID();
        values.add(uuid.toString());
    }
    
    long start = System.nanoTime();
    long seqCount = values.stream().filter(s -> s.startsWith("1")).count();
    long end = System.nanoTime();
    System.out.println("sequential stream took " + (end - start) / 1000 + " ms");
    
    start = System.nanoTime();
    long parCount = values.parallelStream().filter(s -> s.startsWith("1")).count();
    end = System.nanoTime();
    System.out.println("parallel stream took " + (end - start) / 1000 + " ms");
    
    assertEquals(seqCount, parCount);
    System.out.println("seqCount=" + seqCount + ", parCount=" + parCount);
  }

}
