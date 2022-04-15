package com.algorithm.leetcode;

import java.util.*;

public class No78 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3};

        List<List<Integer>> ans = subsets(a);

        System.out.println(ans);
    }

    public static List<List<Integer>> subsets(int[] nums) {
        Deque<Integer> path = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, 0, ans, path);
        return ans;
    }

    public static void dfs(int[] nums, int k, List<List<Integer>> ans, Deque<Integer> path) {
        // 回头条件，不超过数组长度
        if (k == nums.length) {
            ans.add(new ArrayList<>(path));
            System.out.println(path);
            return;
        }

        // 进入条件：选择当前节点，并递归下一层
        path.add(nums[k]);
        dfs(nums, k + 1, ans, path);

        // 进入条件：不选择当前节点，并递归下一层
        path.removeLast();
        dfs(nums, k + 1, ans, path);
    }
}
