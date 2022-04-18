package com.algorithm.leetcode;

/**
 * 寻找两个正序数组的中位数
 */
public class No4 {

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }

    /**
     * 假合并数组，合并到中位时，记下该值（奇数），记下前后值（偶数）
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int u = 0;
        int d = 0;
        int select = 0;
        // 中位数一定出现在两数组总长度的中间，考虑奇偶数情况
        for (int i = 0; i < len/2 + len%2; i++) {
            if (u < nums1.length && d < nums2.length) {
                // 两数组均未取完，则确定取一个小值
                if (nums1[u] <= nums2[d]) {
                    select = nums1[u];
                    u++;
                } else {
                    select = nums2[d];
                    d++;
                }
            } else if (u < nums1.length) {
                // 下数组取完
                select = nums1[u];
                u++;
            } else if (d < nums2.length) {
                // 上数组取完
                select = nums2[d];
                d++;
            } else {
                break;
            }
        }
        // 取完二分之一的数后，最后指针处有一个最小的待选值
        int cur = 0;
        if (u < nums1.length && d < nums2.length) {
            cur = Math.min(nums1[u],  nums2[d]);
        } else if (u < nums1.length) {
            cur = nums1[u];
        } else if (d < nums2.length) {
            cur = nums2[d];
        }
        // 结合奇偶数组的情况：奇数时，就是已选值；偶数时，需要已选值和当前值的平均。
        return len % 2 == 0 ? (select + cur) * 1.0 / 2 : select;
    }
}
