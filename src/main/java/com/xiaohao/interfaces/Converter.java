package com.xiaohao.interfaces;

/**
 * A so called functional interface must contain exactly one abstract method declaration.
 * Each lambda expression of that type will be matched to this abstract method.
 * Since default methods are not abstract you're free to add default methods
 * to your functional interface.
 */
@FunctionalInterface
public interface Converter<F, T> {
  T convert(F f);
}