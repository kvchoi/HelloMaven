package com.algorithm.leetcode;

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
        int last = 0;
        for (int i = 0; i < len/2; i++) {
            if (u < nums1.length && d < nums2.length) {
                if (nums1[u] <= nums2[d]) {
                    last = nums1[u];
                    u = u < nums1.length - 1? u+1 : u;
                } else {
                    last = nums2[d];
                    d = d < nums2.length - 1? d+1 : u;
                }
            } else if (u < nums1.length) {
                last = nums1[u];
                u = u < nums1.length - 1? u+1 : u;
            } else if (d < nums2.length) {
                last = nums2[d];
                d = d < nums2.length - 1? d+1 : u;
            } else {
                break;
            }
        }
        int min = Math.min(nums1[u], nums2[d]);
        return len % 2 == 0 ? (last + min) * 1.0 / 2 : min;
    }
}
