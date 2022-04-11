package com.algorithm.force_opt;

import java.util.Arrays;

public class TwoSumMaxLessThanK {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9, 10, 21, 4, 8, 55};

        int k = 10;

        force(a, k);
    }

    public static void force(int[] a, int k) {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        int left = 0;
        int right = a.length - 1;
        int max = -1;

        while (left < right) {
            int tmp = a[left] + a[right];
            if (tmp >= k) {
                right--;
            } else {
                max = Math.max(max, tmp);
                System.out.println("left=" + a[left] + ",right=" + a[right] + ",max=" + max);
                left++;
            }
        }

    }
}
