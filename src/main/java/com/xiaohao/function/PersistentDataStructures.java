package com.xiaohao.function;

public class PersistentDataStructures {

  public static void main(String[] args) {
    ListNode a = new ListNode(1, null);
    ListNode b = new ListNode(2, null);
    System.out.println("Before Link a=" + a + ", b=" + b);
    ListNode c = ListNode.link(a, b);
    System.out.println("After Link a=" + a + ", b=" + b + ", c=" + c);

    PersistentListNode pa = new PersistentListNode(1, null);
    PersistentListNode pb = new PersistentListNode(2, null);
    System.out.println("Before Link pa=" + pa + ", pb=" + b);
    PersistentListNode pc = PersistentListNode.link(pa, pb);
    System.out.println("After Link pa=" + pa + ", pb=" + pb + ", pc=" + pc);

  }

}

class ListNode {
  private int value;
  private ListNode next;

  public ListNode(int value, ListNode next) {
    this.value = value;
    this.next = next;
  }

  public static ListNode link(ListNode a, ListNode b) {
    if (a == null) {
      return b;
    }
    ListNode node = a;
    while (node.next != null) {
      node = node.next;
    }
    node.next = b;
    return a;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    ListNode iter = this;
    while (iter != null) {
      sb.append(iter.value);
      if (iter.next != null) {
        sb.append("->");
      }
      iter = iter.next;
    }
    return sb.toString();
  }
}

class PersistentListNode {
  private final int value;
  private final PersistentListNode next;

  public PersistentListNode(int value, PersistentListNode next) {
    this.value = value;
    this.next = next;
  }

  public static PersistentListNode link(PersistentListNode a, PersistentListNode b) {
    return a == null ? b : new PersistentListNode(a.value, link(a.next, b));
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    PersistentListNode iter = this;
    while (iter != null) {
      sb.append(iter.value);
      if (iter.next != null) {
        sb.append("->");
      }
      iter = iter.next;
    }
    return sb.toString();
  }
}