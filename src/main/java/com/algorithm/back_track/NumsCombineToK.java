package com.algorithm.back_track;

import java.util.*;

/**
 * 回溯法
 */
public class NumsCombineToK {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 4, 4, 8};

        int k = 2;

        List<List<Integer>> ans = new ArrayList<>();

        //force(nums, k, ans);

        //fast(nums, k, ans);

        Deque<Integer> path = new ArrayDeque<>();
        dfs(nums, k, 0, path, ans);

        System.out.println(ans);

    }

    /**
     * 组合遍历法（从所有组合中取出k个元素的组合）
     *
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
     * 暴力遍历法
     *
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

    /**
     * 深度优先遍历法
     */
    public static void dfs(int[] nums, int k, int index, Deque<Integer> path, List<List<Integer>> ans) {
        // 递归终止条件是：path 的长度等于 k
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }
        // 遍历所有可能的搜索起点，但是可以有一个提前剪枝条件：即剩下的数已经不可能够凑够k个了
        for (int i = index; i < nums.length - (k - path.size()) + 1; i++) {
            // 同一层前后两个相同值，不重复取
            if (i == index || nums[i] != nums[i - 1]) {
                // 向路径变量里添加一个数
                path.addLast(nums[i]);
                // 下一轮搜索，设置的搜索起点要加 1，因为组合数理不允许出现重复的元素
                dfs(nums, k, i + 1, path, ans);
                // 重点理解这里：深度优先遍历有回头的过程，因此递归之前做了什么，递归之后需要做相同操作的逆向操作
                path.removeLast();
            }
        }

    }

}
