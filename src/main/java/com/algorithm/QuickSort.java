package com.algorithm;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 3, 1, 9, 10, 21, 4};

        quickSort(a);

        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a) {
        int left = 0;
        int right = a.length - 1;
        quickSort(a, left, right);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        // 确定基准位置
        int index = getIndex(arr, low, high);

        // 左右再分治
        quickSort(arr, low, index - 1);
        quickSort(arr, index + 1, high);

    }

    private static int getIndex2(int[] array, int left, int right) {
        // base中存放基准数
        int base = array[left];
        int i = left, j = right;
        while (i != j) {
            // 顺序很重要，先从右边开始往左找，直到找到比base值小的数
            while (array[j] >= base && i < j) {
                j--;
            }

            // 再从左往右边找，直到找到比base值大的数
            while (array[i] <= base && i < j) {
                i++;
            }

            // 上面的循环结束表示找到了位置或者(i>=j)了，交换两个数在数组中的位置
            if (i < j) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }

        // 将基准数放到中间的位置（基准数归位）
        array[left] = array[i];
        array[i] = base;

        return i;
    }


    private static int getIndex(int[] arr, int low, int high) {
        // 用左指针挖坑
        int tmp = arr[low];
        while (low < high) {
            // 用右指针找一个比tmp少的来填
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 右指针找到目标填坑，此时右指针处出现一个新坑
            arr[low] = arr[high];
            // 再用左指针找一个比tmp大的来填
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 左指针找到目标填坑，此时左指针处出现一个新坑
            arr[high] = arr[low];
            // 循环下一轮，继续填...
        }
        // 当两指针相碰，确定基准值位置，tmp回填
        arr[low] = tmp;
        // 返回 low 或者 high
        return low;
    }


}
