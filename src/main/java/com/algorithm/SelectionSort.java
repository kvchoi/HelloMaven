package com.algorithm;

import java.util.Arrays;

public class SelectionSort {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9};

        int minIndex = 0;
        // 遍历每个位置
        for (int i = 0; i < a.length; i++) {
            // 找出比这个位置小的元素位置，相等不算
            for (int j = i; j < a.length; j++) {
                if (a[j] < a[i]) {
                    minIndex = j;
                }
            }
            // 交换元素
            int tmp = a[minIndex];
            a[minIndex] = a[i];
            a[i] = tmp;
        }

        System.out.println(Arrays.toString(a));
    }
}
