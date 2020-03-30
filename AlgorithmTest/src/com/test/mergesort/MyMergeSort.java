package com.test.mergesort;

import java.util.HashMap;
import java.util.Map;

public class MyMergeSort {

  public static void main(String args[]){
    int[] a = {42,30,68,98,86,15,57};
    sort(a, 0, a.length-1);
    for (int i : a) {
      System.out.print(i+" ");
    }
  }



  public static void sort(int[] a, int left, int right) {
    if (left >= right)
      return;

    int center = (left + right) >> 1;
    sort(a, left, center);
    sort(a, center + 1, right);
    merge(a, left, center, right);
  }

  public static void merge(int[] data, int left, int center, int right) {
    int[] tmpArr = new int[right+1];
    int mid = center + 1;
    int index = left; // index记录临时数组的索引
    int tmp = left;

    // 从两个数组中取出最小的放入中临时数组
    while (left <= center && mid <= right) {
      tmpArr[index++] = (data[left] <= data[mid]) ? data[left++]: data[mid++];
    }
    // 剩余部分依次放入临时数组
    while (mid <= right) {
      tmpArr[index++] = data[mid++];
    }
    while (left <= center) {
      tmpArr[index++] = data[left++];
    }
    // 将临时数组中的内容复制回原数组
    for (int i = tmp; i <= right; i++) {
      data[i] = tmpArr[i];
    }
  }
}
