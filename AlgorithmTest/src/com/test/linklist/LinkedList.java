package com.test.linklist;

public class LinkedList {
  private ListNode successor = null;

  public ListNode resverse(ListNode head){
    if(head.next == null){
      return head;
    }
    ListNode lastNode = resverse(head.next);
    head.next.next = head;
    head.next = null;
    return lastNode;
  }

  public ListNode resverseN(ListNode head, int n){
    if(n == 1){
      successor = head.next;
      return head;
    }
    ListNode lastNode = resverseN(head.next, n-1);

    head.next.next = head;
    head.next = successor;
    return lastNode;
  }

  ListNode resverseBetween(ListNode head, int m, int n){
    if (m == 1){
      return resverseN(head, n);
    }
    head.next = resverseBetween(head.next, m-1, n-1);
    return head;
  }
}
