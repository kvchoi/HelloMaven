package com.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class No3 {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

    //滑动窗口法
    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        // 需要一个去重缓存
        Set<Character> exist = new HashSet<>();
        // 需要一个指针保存当前搜索位置
        int rk = 0;

        // 顺序切换起点
        for (int i = 0; i < s.length(); i++) {
            // 删除前一个起点
            if (i != 0) {
                exist.remove(s.charAt(i - 1));
            }
            // 从rk继续向前探索最长可能
            while (rk < s.length() && !exist.contains(s.charAt(rk))) {
                exist.add(s.charAt(rk));
                rk++;
            }
            max = Math.max(max, rk - i);
        }
        return max;
    }
}
