package com.algorithm.force_opt;

import java.util.Arrays;

public class TwoSumLessThanK {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9, 10, 21, 4, 8, 55};

        int k = 10;

        force(a, k);
//        fast(a, k);

    }

    public static void force(int[] a, int k) {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        for (int i = 0; i < a.length; i++) {
            if (a[i] >= k) {
                break;
            }
            for (int j = i + 1; j < a.length; j++) {
                int sum = a[i] + a[j];
                if (sum <= k) {
                    System.out.println("i=" + i + ", j=" + j + ", sum=" + sum);
                } else {
                    break;
                }
            }
        }
    }

    public static void fast(int[] a, int k) {
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        int l = 0;
        int r = a.length - 1;

        while (l < r) {
            int t = a[l] + a[r];
            if (t > k) {
                r--;
            } else {
                System.out.println("a[l]=" + a[l] + " ,a[r]=" + a[r]);
                l++;
            }
        }
    }
}
