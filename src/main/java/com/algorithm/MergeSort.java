package com.algorithm;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9, 10, 21, 4, 8, 55};

        int[] b = devide(a);

        System.out.println(Arrays.toString(b));
    }

    public static int[] devide(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return arr;
        }
        int index = n / 2;
        int[] arr1 = devide(Arrays.copyOfRange(arr, 0, index));
        int[] arr2 = devide(Arrays.copyOfRange(arr, index, arr.length));
        return merge(arr1, arr2);
    }

    public static int[] merge(int[] arr1, int[] arr2) {
        if (arr1.length == 0) {
            return arr2;
        }
        if (arr2.length == 0) {
            return arr1;
        }
        int[] newArr = new int[arr1.length + arr2.length];

        // 三指针才可以合并排序
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                newArr[k++] = arr1[i++];
            } else {
                newArr[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            newArr[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            newArr[k++] = arr2[j++];
        }

        // 单指针只能混排
//        int n = Math.max(arr1.length, arr2.length);
//        int k = Math.min(arr1.length, arr2.length);
//        for (int i = 0; i < n; i++) {
//            if (i < arr1.length && i < arr2.length) {
//                if (arr1[i] <= arr2[i]) {
//                    newArr[i * 2] = arr1[i];
//                    newArr[i * 2 + 1] = arr2[i];
//                } else {
//                    newArr[i * 2] = arr2[i];
//                    newArr[i * 2 + 1] = arr1[i];
//                }
//            } else if (i >= arr1.length && i < arr2.length) {
//                newArr[k + i] = arr2[i];
//            } else if (i >= arr2.length && i < arr1.length) {
//                newArr[k + i] = arr1[i];
//            }
//        }
        return newArr;
    }
}
