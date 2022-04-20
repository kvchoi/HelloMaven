package com.algorithm.leetcode;

/**
 * 8. 字符串转换整数 (atoi)
 */
public class No8 {

    public static void main(String[] args) {
        System.out.println(myAtoi("2147483648"));
    }

    public static int myAtoi(String s) {
        int ans = 0;
        int flag = 1;
        int index = 0;

        // 去掉前面的空格
        while (index < s.length()) {
            char idx = s.charAt(index);
            if (idx == ' ') {
                index++;
            } else {
                break;
            }
        }

        // 识别正负号（多个判为无效）
        if (index < s.length()) {
            char idx = s.charAt(index);
            if (idx == '-') {
                flag *= -1;
                index++;
            } else if (idx == '+') {
                index++;
            }
        }
        if (index < s.length()) {
            char idx = s.charAt(index);
            if (idx == '+' || idx == '-') {
                return ans;
            }
        }

        // 开始解释数字
        for (int i = index; i < s.length(); i++) {
            char idx = s.charAt(i);
            if (idx < '0' || idx > '9') {
                break;
            }
            // 如果当前值再推进会超出最小整数，则停止
            // 如果当前值再推进刚好不超出最小整数，但是如果加上个位数时超出了，则停止
            if (ans < Integer.MIN_VALUE / 10
                    || (ans == Integer.MIN_VALUE / 10 && -(idx - '0') < Integer.MIN_VALUE % 10)) {
                return Integer.MIN_VALUE;
            }
            // 如果当前值再推进会超出最大整数，则停止
            // 如果当前值再推进刚好不超出最大整数，但是如果加上个位数时超出了，则停止
            if (ans > Integer.MAX_VALUE / 10
                    || (ans == Integer.MAX_VALUE / 10 && (idx - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            // 推进结果
            ans = ans * 10 + (idx - '0') * flag;
        }
        return ans;
    }

}
