package com.algorithm.leetcode;

/**
 * 11. 盛最多水的容器
 */
public class No11 {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(arr));
    }

    // 左右指针碰撞法，相对暴力法双重循环，可以省去不必要的比较，（每次保留最高水位，再收缩范围）
    public static int maxArea(int[] height) {
        int max = 0;
        int l = 0;
        int r = height.length - 1;
        while (l < r) {
            int s = Math.min(height[l], height[r]) * (r - l);
            max = Math.max(max, s);
            if (height[l] > height[r]) {
                r--;
            } else {
                l++;
            }
        }
        return max;
    }

}
