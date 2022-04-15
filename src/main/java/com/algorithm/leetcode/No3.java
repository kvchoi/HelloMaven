package com.algorithm.leetcode;

import java.util.HashSet;
import java.util.Set;

public class No3 {

    public static void main(String[] args) {
//        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        Set<Character> exist = new HashSet();

        int rk = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                exist.remove(s.charAt(i - 1));
            }
            while (rk < s.length() && !exist.contains(s.charAt(rk))) {
                exist.add(s.charAt(rk));
                rk++;
            }
            max = Math.max(max, rk - i);
        }
        return max;
    }
}
