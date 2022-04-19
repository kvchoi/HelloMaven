package com.algorithm.leetcode;

import java.util.*;

/**
 * 全排列（无重复元素）
 */
public class No46 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3};

        List<List<Integer>> ans = permute(a);

        System.out.println(ans);
    }

    public static List<List<Integer>> permute(int[] nums) {
        Deque<Integer> path = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();
        // 关键点，标记数组，记录元素全局使用情况
        boolean[] used = new boolean[nums.length];

        dfs(nums, 0, ans, path, used);
        return ans;
    }

    public static void dfs(int[] nums, int k, List<List<Integer>> ans, Deque<Integer> path, boolean[] used) {
        // 回头条件，不超过数组长度
        if (k == nums.length) {
            ans.add(new ArrayList<>(path));
            System.out.println(path);
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                // 如果上层已用过
                continue;
            }
            // 进入：当前开始使用
            used[i] = true;
            path.add(nums[i]);
            dfs(nums, k + 1, ans, path, used);
            // 退出：不再使用
            used[i] = false;
            path.remove(nums[i]);
        }

    }
}
