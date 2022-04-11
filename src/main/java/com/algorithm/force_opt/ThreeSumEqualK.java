package com.algorithm.force_opt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSumEqualK {

    public static void main(String[] args) {
        int[] a = new int[]{-1, 0, 1, 2, -1, -4};

        int target = 0;

        List<List<Integer>> ans = threeSumFast(a, target);

        System.out.println(Arrays.toString(a));

        System.out.println(ans);
    }

    /**
     * 第一重循环求a数是n次比较，第二重包含并列查找b和c两个数，也是最多n次比较，因此O(n^2)的复杂度
     */
    public static List<List<Integer>> threeSumFast(int[] a, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            // 第一重，第一个比较完，后面跳过相同的数
            if (i == 0 || a[i] != a[i - 1]) {

                int k = a.length - 1;
                for (int j = i + 1; j < a.length; j++) {
                    // 第二重，第一个比较完，后面也要跳过相同的数
                    if (j == i + 1 || a[j] != a[j - 1]) {
                        // 第二重，如果前后指针相撞，不用看了，必是没有合适的
                        if (j == k) {
                            break;
                        }
                        // 第三个数，在第二重循环并行从后面向前找，因为前两个数确定，第三个数必然唯一
                        while (k > j && a[i] + a[j] + a[k] > target) {
                            // 查找第三个可能合适的数，它必然是在第二个数右边
                            k--;
                        }

                        // 检查这三元素是否满足要求
                        if (a[i] + a[j] + a[k] == target) {
                            List<Integer> tmp = new ArrayList<>();
                            tmp.add(a[i]);
                            tmp.add(a[j]);
                            tmp.add(a[k]);
                            ans.add(tmp);
                        }
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 暴力法，三重循环框架都是n次比较，O(n^3)的复杂度
     */
    public static List<List<Integer>> threeSumForce(int[] a, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            // 第一重，第一个比较完，后面跳过相同的数
            if (i == 0 || a[i] != a[i - 1]) {
                for (int j = i + 1; j < a.length; j++) {
                    // 第二重，第一个比较完，后面也要跳过相同的数
                    if (j == i + 1 || a[j] != a[j - 1]) {
                        for (int k = j + 1; k < a.length; k++) {
                            // 第三重，第一个比较完，后面也要跳过相同的数
                            if (k == j + 1 || a[k] != a[k - 1]) {
                                // 检查三元素
                                if (a[i] + a[j] + a[k] == target) {
                                    List<Integer> tmp = new ArrayList<>();
                                    tmp.add(a[i]);
                                    tmp.add(a[j]);
                                    tmp.add(a[k]);
                                    ans.add(tmp);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ans;
    }
}
