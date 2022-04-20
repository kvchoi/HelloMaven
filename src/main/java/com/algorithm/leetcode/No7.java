package com.algorithm.leetcode;

/*
 * @lc app=leetcode.cn id=7 lang=java
 *
 * [7] 整数反转
 */

// @lc code=start
class No7 {

    public static void main(String[] args) {
        System.out.println(new No7().reverse(123));
    }

    public int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            // 如果当前值已经超出最小/最大值范围，则无法再进行推入
            if (ans < Integer.MIN_VALUE / 10 || ans > Integer.MAX_VALUE / 10) {
                return 0;
            }
            // 求出个位数(带符号)
            int digits = x % 10;
            // 求出退一位
            x /= 10;
            // 向结果数推一位
            ans = ans * 10 + digits;
        }

        return ans;

    }
}
// @lc code=end

