package com.algorithm.force_opt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumEqualK {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9, 10, 21, 4, 8, 55};
        int k = 9;

        fast(a, k);
        System.out.println("========");
        force(a, k);
    }

    // 中间结果暂存,O(n)
    public static void fast(int[] a, int k) {
        Map<Integer, Integer> remainMap = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int n = k - a[i];
            if (remainMap.containsKey(n)) {
                System.out.println("i=" + a[remainMap.get(n)] + ", j=" + a[i]);
            } else {
                remainMap.put(a[i], i);
            }
        }
    }

    // 双指针对撞,O(n^2)
    public static void force(int[] a, int k) {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));;

        for (int i = 0; i < a.length; i++) {
            for (int j = a.length - 1; j > i; j--) {
                if (a[i] + a[j] == k) {
                    System.out.println("i=" + a[i] + ", j=" + a[j]);
                }
            }
        }

    }

}
