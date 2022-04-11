package com.algorithm;

import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9};

        // 从第二张牌开始
        for (int i = 1; i < a.length; i++) {
            // 当前的牌，分别和前1、2、3、n张牌进行对比，如果小就向前挪一个位置
            for (int j = i; j > 0 && a[j] <= a[j-1]; j--) {
                int tmp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = tmp;
            }
        }

        System.out.println(Arrays.toString(a));
    }
}
