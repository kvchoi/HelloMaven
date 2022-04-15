package com.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class No6 {
    public static void main(String[] args) {
        System.out.println(convert2("PAYPALISHIRING", 3));

    }

    // 跳跃法阅读法
    public static String convert2(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        StringBuilder ans = new StringBuilder();
        // 第一行，全字串跳两倍的位置
        for (int i = 0; i < s.length(); i += (numRows - 1) * 2) {
            ans.append(s.charAt(i));
        }
        // 第二行，到N-1行
        for (int i = 1; i < numRows - 1; i++) {
            //
            for (int j = 0; j * (numRows - 1) * 2 + i < s.length(); j++) {
                // 一上
                ans.append(s.charAt(j * (numRows - 1) * 2 + i));
                // 一下
                int r = (j + 1) * (numRows - 1) * 2 - i;
                if (r < s.length()) {
                    ans.append(s.charAt(r));
                }
            }
        }
        // 最后一行，同第一行
        for (int i = numRows - 1; i < s.length(); i += (numRows - 1) * 2) {
            ans.append(s.charAt(i));
        }
        return ans.toString();
    }

    // 直接填空法
    public static String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }

        List<List<Character>> ans = new ArrayList<>(numRows);
        int flag = -1;
        for (int i = 0, r = 0; i < s.length(); i++, r += flag) {
            if (ans.size() < r + 1) {
                ans.add(new ArrayList<>());
            }
            ans.get(r).add(s.charAt(i));
            if (r >= numRows - 1 || r <= 0) {
                flag *= -1;
            }
        }
        // 遍历
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ans.size(); i++) {
            ans.get(i).forEach(sb::append);
        }
        return sb.toString();
    }

}
