package com.algorithm.back_track;

import java.util.ArrayList;
import java.util.List;

public class NumsCombineToK {


    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 4, 4, 8};

        int k = 2;

        List<List<Integer>> ans = new ArrayList<>();

        // 收集第一层
        for (int i = 0; i < nums.length - k + 1; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                bt(nums, i, ans, new ArrayList<>(), k);
            }
        }

        System.out.println(ans);

    }

    public static void bt(int[] nums, int index, List<List<Integer>> ans, List<Integer> tmp, int k) {
        // 收集当前层
        if (index < nums.length) {
            tmp.add(nums[index]);
        }
        // 不收集下一层
        if (k <= 1) {
            ans.add(new ArrayList<>(tmp));
            return;
        }
        // 开始收集下一层
        for (int i = index + 1; i < nums.length; i++) {
            if (i == index + 1 || nums[i] != nums[i - 1]) {
                bt(nums, i, ans, tmp, k - 1);
                if (!tmp.isEmpty()) {
                    tmp.remove(tmp.size() - 1);
                }
            }
        }
    }


}
