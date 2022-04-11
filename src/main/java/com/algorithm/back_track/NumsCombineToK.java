package com.algorithm.back_track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 回溯法
 */
public class NumsCombineToK {


    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 4, 4, 8};

        int k = 2;

        List<List<Integer>> ans = new ArrayList<>();

        //force(nums, k, ans);

        fast(nums, k, ans);

        System.out.println(ans);

    }

    /**
     * 第一重循环复杂度为O(2^n)，第二重最多O(32)，整体还是O(2^n)
     */
    public static void fast(int[] nums, int k, List<List<Integer>> ans) {

        // n个元素映射到n位的bit
        int len = nums.length;
        for (int i = 0; i < (1 << len); i++) {
            // 只选k个数的情况
            if (bitCount(i) == k) {
                List<Integer> tmp = new ArrayList<>();
                // 倒序按位取数
                for (int s = i, j = len - 1; s > 0; j--, s = s >> 1) {
                    // 是否取数
                    if ((s & 1) == 1) {
                        tmp.add(nums[j]);
                    }
                }
                // 排序和去重
                Collections.sort(tmp);
                if (!ans.contains(tmp)) {
                    ans.add(tmp);
                }
            }
        }
        Collections.reverse(ans);
    }

    private static int bitCount(int num) {
        int count = 0;
        while (num > 0) {
            if ((num & 1) == 1) {
                count++;
            }
            num = num >> 1;
        }
        return count;
    }

    /**
     * 每一层for都是O(n)，根据k的个数叠加，整体是O(n^k)
     */
    public static void force(int[] nums, int k, List<List<Integer>> ans) {
        // 开始遍历第一层，注意只需要遍历到剩下k-1个，因为剩下不足k个没必要看了
        for (int i = 0; i < nums.length - (k - 1); i++) {
            // 同一层前后相同的值，不重复遍历
            if (i == 0 || nums[i] != nums[i - 1]) {
                bt(nums, i, ans, new ArrayList<>(), k);
            }
        }
    }

    private static void bt(int[] nums, int index, List<List<Integer>> ans, List<Integer> tmp, int k) {
        // 收集当前层
        if (index < nums.length) {
            tmp.add(nums[index]);
        }
        // 是否遍历下一层
        if (k <= 1) {
            ans.add(new ArrayList<>(tmp));
            return;
        }
        // 开始遍历下一层
        for (int i = index + 1; i < nums.length; i++) {
            // 同一层前后两个相同值，不重复遍历
            if (i == index + 1 || nums[i] != nums[i - 1]) {
                // 进入收集
                bt(nums, i, ans, tmp, k - 1);
                if (!tmp.isEmpty()) {
                    tmp.remove(tmp.size() - 1);
                }
            }
        }
    }


}
