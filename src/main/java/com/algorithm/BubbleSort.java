package com.algorithm;

import java.util.Arrays;

public class BubbleSort extends BaseOperations {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9};
        // 确定需要进行N轮
        for (int i = 0; i < a.length; i++) {
            // 每轮从0开始，到上轮确定值之前终止
            for (int j = 0; j + 1 < a.length - i; j++) {
                // 遇到大值，则交换之
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
            // 当前轮结果
            System.out.println("第" + (i + 1) + "轮：" + Arrays.toString(a));
        }

        System.out.println(Arrays.toString(a));
    }

}
