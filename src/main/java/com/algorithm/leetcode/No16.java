package com.algorithm.leetcode;

import java.util.Arrays;

public class No16 {

    public static void main(String[] args) {
        int[] a = new int[]{-1, 2, 1, -4};
        int target = 1;
        int ret = threeSumClosest(a, target);
        System.out.println(ret);
    }

    /**
     * 排序后，双指针碰撞，复杂度降至n^2。
     *
     * 优化方法有：
     * 1，第2第3个数的选择，通过双指针逼近得出；（此时，需要有序数组的支持）
     * 2，考虑快进的情况；
     * 3，
     */
    public static int threeSumClosest(int[] nums, int target) {
        int min = Integer.MAX_VALUE;
        int a = 0, b = 0, c = 0;
        // 先排下序
        Arrays.sort(nums);

        // 第一个数，还是n种选择可能的
        for (int i = 0; i < nums.length - 2; i++) {
            // 剩下的两个数，就通过双针对碰撞来选取
            int l = i + 1;
            int r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    return sum;
                }
                // 从坐标远离距离上判断是否接近，并记录找到的第一个最接近的组合
                if (Math.abs(sum - target) < min) {
                    min = Math.abs(sum) - target;
                    a = i;
                    b = l;
                    c = r;
                }
                // 碰撞指针移动时，要注意大盘的情况，分别从再边逐步向中间靠近
                if (sum > target) {
                    // 超过目标时，尝试缩小大值
                    r--;
                    while (r - 1 > l && nums[r] == nums[r - 1]) {
                        // 还可以再走一步，因此前面有个值一样的
                        r--;
                    }
                } else {
                    // 小于目标时，尝试增加小值
                    l++;
                    while ((l + 1 < r && nums[l] == nums[l + 1])) {
                        // 还可以再走一步，因此前面有个值一样的
                        l++;
                    }
                }
            }
        }
        System.out.println(nums[a] + "," + nums[b] + "," + nums[c]);
        return nums[a] + nums[b] + nums[c];
    }

    /**
     * 暴力法，三重遍历查询。
     * <p>
     * 优化方案：
     * 1，n^3的复杂度，怎样降低到n^2，或者nlogn，或者n，或者logn呢？
     * 2，操作压缩、空间变换，甚至模型和算法重新映射问题集呢？
     * 3，边界收缩/剪枝，是否可以提前实施？
     */
    public static int force(int[] nums, int target) {
        int min = Integer.MAX_VALUE;
        int a = 0, b = 0, c = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    int res = nums[i] + nums[j] + nums[k];
                    if (res == target) {
                        return res;
                    }
                    // 从坐标远离距离上判断，第一个最接近的组合
                    if (Math.abs(res - target) < min) {
                        min = Math.abs(res) - target;
                        a = i;
                        b = j;
                        c = k;
                    }
                }
            }
        }
        System.out.println(nums[a] + "," + nums[b] + "," + nums[c]);
        return nums[a] + nums[b] + nums[c];
    }

}
