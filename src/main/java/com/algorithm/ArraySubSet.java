package com.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * 从指定数组中取出指定数量的值，组合成子数组。可以理解n位bitset样本上，取0或1。
 */
public class ArraySubSet {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4};
        List<List<Integer>> result = new ArrayList<>();

        getArr(a, result);
        System.out.println(result);
    }

    private static void getArr(int[] a, List<List<Integer>> result) {

        List<Integer> tmp;

        // 从二进制角度看，每一个元素都有"0未选中，1选中"两种状态，即0到n种可能性
        int n = 1 << a.length;

        for (int i = 0; i < n; i++) {
            tmp = new ArrayList<>();

            // 针对当前样本值表示的可能性，从右往左"按位与"判断是否要选出目标元素值
            int sample = i;
            // 右指针
            int index = a.length - 1;

            while (sample > 0) {
                if ((sample & 1) == 1) {
                    // 是否选中该位置
                    tmp.add(a[index]);
                }
                // 右移
                sample = sample >> 1;
                index--;
            }

            tmp.sort(Comparator.comparing(Integer::intValue));
            result.add(tmp);
        }
    }
}
