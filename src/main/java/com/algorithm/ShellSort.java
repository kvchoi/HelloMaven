package com.algorithm;

import java.util.Arrays;

public class ShellSort {

    public static void main(String[] args) {
        int[] array = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 1};
        System.out.println("排序之前：");
        System.out.println(Arrays.toString(array));

        //定义gap大小
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            //移动这个gap窗口
            for (int i = 0; i < gap; i++) {
                //这个循环里其实就是一个插入排序（gap=1时就好理解）
                for (int j = i + gap; j < array.length; j += gap) {
                    // gap最初位置
                    int k = j - gap;
                    while (k >= 0 && array[k] > array[k + gap]) {
                        int temp = array[k];
                        array[k] = array[k + gap];
                        array[k + gap] = temp;
                        // 发生交换，看看是否能向前一个gap位置再交换
                        k -= gap;
                    }
                }
            }
        }

        System.out.println("排序之后：");
        System.out.println(Arrays.toString(array));
    }
}
