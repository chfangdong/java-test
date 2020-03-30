package com.test.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HeapMain {

  public static void main(String args[]) {
//    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
//    priorityQueue.add(1);
//    priorityQueue.poll();
//    priorityQueue.size();

//    HeapOperator heapOperator = new HeapOperator();
//    int[] arr = new int[]{1, 6, 2, 3, 5, 4, 8, 7, 9};
//    int result[] = heapOperator.findTopK(arr, 5);
//
//    for (int i = 0; i < result.length; i++) {
//      System.out.println(result[i]);
//    }

    PriorityQueue<Integer> heap = new PriorityQueue<>();
    int[] arr = new int[]{1, 6, 2, 3, 5, 4, 8, 7, 9};
    for (int i = 0; i < arr.length; i++) {
      heap.add(arr[i]);
    }

    for (int i = 0; i < heap.size(); i++) {
      System.out.println(heap.poll());
    }

    String s = "11122342";
    s.startsWith("0");

    String b = "aaa";
    List<String> aList = new ArrayList<>();
    aList.remove(2);
    String.join(".", aList);

  }
}
