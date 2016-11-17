package com.xiaohao;

public class Apple implements Comparable<Apple> {

  public enum Color {
    GREEN, RED, YELLOW;
  }

  private int weight;
  private Color color;

  public Apple() {
    
  }
  
  public Apple(int weight) {
    this.weight = weight;
  }
  
  public Apple(int weight, Color color) {
    this.weight = weight;
    this.color = color;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return "Apple, color : " + color + ", weight : " + weight;
  }

  @Override
  public int compareTo(Apple o) {
    return weight - o.weight;
  }
}
