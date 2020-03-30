package com.test.queue;

public class BoundedBlockingQueue {
  int capacity;
  int count;
  int putIndex;
  int takeIndex;
  int array[];

  public BoundedBlockingQueue(int capacity) {
    this.capacity = capacity;
    this.count = 0;
    this.putIndex = 0;
    this.array = new int[capacity];
  }

  public void enqueue(int element) throws InterruptedException {
    synchronized (this){
      while (count == capacity){
        this.wait();
      }

      array[putIndex] = element;
      if (++putIndex == capacity){
        putIndex = 0;
      }

      count++;
      this.notifyAll();
    }
  }

  public int dequeue() throws InterruptedException {
    synchronized (this){
      while (count == 0){
        wait();
      }

      int data = array[takeIndex];
      count--;
      if (++takeIndex == capacity){
        takeIndex = 0;
      }
      notifyAll();
      return data;
    }
  }

  public int size() {
    synchronized (this){
      return count;
    }
  }
}
