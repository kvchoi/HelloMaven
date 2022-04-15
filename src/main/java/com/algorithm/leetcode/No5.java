package com.algorithm.leetcode;

import java.util.Arrays;

public class No5 {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("aaaaaaaa"));
    }

    //暴力求解，列举所有的子串，判断是否为回文串，保存最长的回文串。
    public static String longestPalindrome2(String s) {
        return "";
    }

    //扩散法，先左右分别撑大，再左右同时撑大
    public static String longestPalindrome(String s) {
        int max = 0;
        int max_l = 0;
        int max_r = 0;
        char[] charArr = s.toCharArray();
        int count = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int l = i, r = i;
            // 往左撑大
            while (l - 1 >= 0 && s.charAt(l - 1) == s.charAt(i)) {
                l--;
                count++;
            }
            // 往右撑大
            while (r + 1 < s.length() && s.charAt(r + 1) == s.charAt(i)) {
                r++;
                count++;
            }
            // 左右同时撑大
            while (l - 1 >= 0 && r + 1 < s.length() && charArr[l - 1] == charArr[r + 1]) {
                l--;
                r++;
                count++;
            }
            if (max < r - l + 1) {
                max = r - l + 1;
                max_l = l;
                max_r = r;
            }
        }
        System.out.println(count);
        return new String(Arrays.copyOfRange(charArr, max_l, max_r + 1));
    }
}
