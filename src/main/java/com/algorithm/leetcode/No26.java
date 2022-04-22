package com.algorithm.leetcode;

import java.util.Arrays;

/**
 * 删除有序数组中的重复项
 */
public class No26 {

    public static void main(String[] args) {
        int[] a = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};

        System.out.println(removeDuplicates(a));
        System.out.println(Arrays.toString(a));
    }


    // 覆盖法，快慢指针，重复元素位置进行覆盖，n复杂度
    public static int removeDuplicates(int[] nums) {
        // 0或1个元素，不用看就知道
        int n = nums.length;
        if (n == 0 || n == 1) {
            return n;
        }
        // 快指针向前看，并向后移；慢指针被替换后，向后移；
        int fast = 1;
        int slow = 1;
        while (fast < n) {
            // 快指针发现第一个非重复元素，则移动到快指针位置
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                // 坑被用，前进一格
                slow++;
            }
            // 继续前进
            fast++;
        }
        return slow;
    }

    // 暴力法，双指针，移动重复元素到末尾，n^2复杂度
    public static int removeDuplicates2(int[] nums) {
        int k = nums.length - 1;
        for (int i = 0; i < k; i++) {
            // 如果后一个等值重复
            if (nums[i + 1] == nums[i]) {
                // 向后移走
                for (int j = i + 1; j < k; j++) {
                    int tmp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = tmp;
                }
                // 有效位指针前移
                k--;
                // 有可能还会遇到一个等值，需要在当前位置重新判断一次
                i--;
            }
        }
        return k + 1;
    }


}
