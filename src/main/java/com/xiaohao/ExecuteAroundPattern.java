package com.xiaohao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundPattern {

  public static void main(String[] args) {
    String fileName = "/home/xiaohao/github/java8test/src/main/java/com/xiaohao/ExecuteAroundPattern.java";
    System.out.println(processFile(fileName, (BufferedReader br) -> br.readLine()));
    System.out.println(
      processFile(fileName, (BufferedReader br) -> br.readLine() + br.readLine() + br.readLine()));
    
    System.out.println("\nOutput\n");
    System.out.println(
      processFile(fileName, (BufferedReader br) -> {
        String result = "";
        String line;
        while ((line = br.readLine()) != null) {
          result += line + "\n";
        }
        return result;
      }));
  }

  private static String processFile(String fileName, BufferedReaderProcessor p) {
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      return p.process(br);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}

@FunctionalInterface
interface BufferedReaderProcessor {
  String process(BufferedReader br) throws IOException;
}