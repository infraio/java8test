package com.xiaohao.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Lambda1 {

  public static void main(String[] args) {
    List<String> names = Arrays.asList("zhang", "wang", "li", "zhao");
    Collections.sort(names, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o1.compareTo(o2);
      }
    });
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", "li", "zhao");
    Collections.sort(names, (String a, String b) -> {
      return a.compareTo(b);
    });
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", "li", "zhao");
    Collections.sort(names, (String a, String b) -> a.compareTo(b));
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", "li", "zhao");
    Collections.sort(names, (a, b) -> a.compareTo(b));
    System.out.println(names);

    names.sort(Collections.reverseOrder());
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", null, "li", "zhao");
    names.sort(Comparator.nullsLast(String::compareTo));
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", null, "li", "zhao");
    names.sort(Comparator.nullsLast((String a, String b) -> a.compareTo(b)));
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", null, "li", "zhao");
    names.sort(Comparator.nullsLast((a, b) -> a.compareTo(b)));
    System.out.println(names);

    names = null;
    Optional.ofNullable(names).ifPresent(list -> list.sort(Comparator.naturalOrder()));
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", "li", "zhao");
    Optional.ofNullable(names).ifPresent(list -> list.sort(Comparator.naturalOrder()));
    System.out.println(names);

    names = null;
    Optional.ofNullable(names).ifPresent(list -> list.sort((a, b) -> a.compareTo(b)));
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", "li", "zhao");
    Optional.ofNullable(names).ifPresent(list -> list.sort((a, b) -> a.compareTo(b)));
    System.out.println(names);

    names = Arrays.asList("zhang", "wang", null, "li", "zhao");
    Optional.ofNullable(names)
        .ifPresent(list -> list.sort(Comparator.nullsLast((a, b) -> a.compareTo(b))));
    System.out.println(names);
  }

}
