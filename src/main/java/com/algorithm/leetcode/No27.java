package com.algorithm.leetcode;

import java.util.Arrays;

public class No27 {

    public static void main(String[] args) {
        int[] a = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};

        System.out.println(removeDuplicates(a, 2));
        System.out.println(Arrays.toString(a));
    }


    // 覆盖法，快慢指针，目标元素位置进行覆盖，n复杂度
    public static int removeDuplicates(int[] nums, int val) {
        int n = nums.length;
        int left = 0;
        // 快慢指针一起前进
        for (int right = 0; right < n; right++) {
            // 如果是目标值，则快指针会继续前进，不等指针
            if (nums[right] != val) {
                // 如果非目标值，移动到慢指针位置，且慢指针才前进
                nums[left] = nums[right];
                // 让慢指针放心走，因为前面的路，快指针都帮它探过了
                left++;
            }
        }
        return left;
    }

    // 交换法，快慢指针，目标元素位置进行交换位置，n复杂度（参考No26的方法，不损耗目标元素，只是移动位置）
    public static int removeDuplicates2(int[] nums, int val) {
        // 没元素
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // 先定位到第一个目标元素，待命
        int slow = 0;
        for (int i = 0; i < n; i++, slow++) {
            if (nums[i] == val) {
                break;
            }
        }
        // 可惜，目标没有出现
        if (slow == n - 1) {
            return slow;
        }

        // 快指针向前找非目标元素
        for (int fast = slow + 1; fast < n; fast++) {
            if (nums[fast] != val) {
                // 并把它换到慢指针的位置上
                nums[slow] = nums[fast];
                nums[fast] = val;
                // 然后，慢指针继续定位到一个目标位置，待命
                for (; slow < n && slow < fast; slow++) {
                    if (nums[slow] == val) {
                        break;
                    }
                }
            }
        }
        return slow;

    }
}
