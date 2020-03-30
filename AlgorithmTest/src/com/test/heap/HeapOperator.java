package com.test.heap;

import java.util.PriorityQueue;

public class HeapOperator {
  public MaxHeap buildHeap(int array[], int n){
    MaxHeap heap = new MaxHeap(n);

    for (int i = 0; i < n; i++){
      heap.array[i] = array[i];
    }
    heap.count = n;
    for (int i=(n-1)/2; i >= 0; i--){
      heap.percolateDown(i);
    }
    return heap;
  }

  public int[]  findTopK(int nums[], int k){
    PriorityQueue<Integer> heap = new PriorityQueue<>();

    for(int num: nums){
      heap.add(num);
      if (heap.size() > k){
        heap.poll();
      }
    }
    int size = heap.size();
    int result[] = new int[size];
    for (int i = 0; i < size; i++) {
      result[i] = heap.poll();
    }

    return result;
  }
}
