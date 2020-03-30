package com.test.heap;

public class MaxHeap {
  public int array[];
  public int count;
  public int capacity;

  public MaxHeap(int capacity){
    this.capacity = capacity;
    this.count = 0;
    this.array = new int[capacity];
  }

  public int Parent(int i){
    if(i <= 0 || i >= capacity){
      return -1;
    }
    return (i-1)/2;
  }

  public int LeftChild(int i) {
    int left = 2*i+1;
    if(left >= capacity){
      return -1;
    }
    return left;
  }

  public int RightChild(int i) {
    int right = 2*i+2;
    if(right >= capacity){
      return -1;
    }
    return right;
  }

  public int GetMax() {
    if (count == 0) {
      return -1;
    }
    return array[0];
  }

  public void percolateDown(int i){
    int max;
    int left = LeftChild(i);
    int right = RightChild(i);
    if(left != -1 && array[left] < array[i]){
      max = left;
    }else {
      max = i;
    }

    if (right != -1 && array[right] > array[max]) {
      max = right;
    }

    if (max != i){
      int tmp = array[i];
      array[i] = array[max];
      array[max] = tmp;
    }

    percolateDown(max);
  }

  public int deleteMax(){
    if (count == 0){
      return -1;
    }

    int data = array[0];
    array[0] = array[count-1];
    this.count--;
    percolateDown(0);

    return data;
  }

  public void insert(int data){
    if (count == capacity){
      resizeHeap();
    }

    count++;

    int i = count-1;
    for(;i >= 0 && data > array[(i-1)/2];){
      int parentIndex = (i-1)/2;
      array[i] = array[parentIndex];
      i = parentIndex;
    }

    array[i] = data;
  }

  public void resizeHeap(){
    int oldArray[] = new int[capacity];
    System.arraycopy(array, 0, oldArray, 0, capacity);

    array = new int[capacity*2];
    for (int i = 0; i < capacity; i++) {
      array[i] = oldArray[i];
    }

    capacity *= 2;
    oldArray = null;
  }

  public void destoryHeap(){
    count = 0;
    array = null;
  }
}
