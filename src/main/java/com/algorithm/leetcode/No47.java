package com.algorithm.leetcode;

import java.util.*;

/**
 * 全排列（有重复元素）
 */
public class No47 {

    public static void main(String[] args) {
        int[] a = new int[]{1, 3, 2};

        List<List<Integer>> ans = permute(a);

        System.out.println(ans);
    }

    public static List<List<Integer>> permute(int[] nums) {
        Deque<Integer> path = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();
        // 关键点1，标记数组，记录元素全局使用情况
        boolean[] used = new boolean[nums.length];
        // 关键点2，排下序
        Arrays.sort(nums);

        dfs(nums, 0, ans, path, used);
        return ans;
    }

    public static void dfs(int[] nums, int k, List<List<Integer>> ans, Deque<Integer> path, boolean[] used) {
        // 回头条件，不超过数组长度
        if (k == nums.length) {
            List<Integer> order = new ArrayList<>(path);
            Collections.reverse(order);
            ans.add(order);
            System.out.println(path);
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                // 如果同一个元素上层已用过，则本层不能再用
                // 如果是不同位置的等值元素，在前面使用过则本层可以继续使用，否则在前面没有使用过，本层也不能首先使用（剪枝）
                continue;
            }
            // 进入：当前开始使用
            used[i] = true;
            path.push(nums[i]);
            dfs(nums, k + 1, ans, path, used);
            // 退出：不再使用
            used[i] = false;
            path.pop();
        }

    }

}
