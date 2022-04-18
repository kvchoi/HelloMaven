package com.algorithm.leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class No42 {

    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] arr2 = new int[]{4, 2, 0, 3, 2, 5};
        int[] arr3 = new int[]{2, 0, 2};
        int[] arr4 = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
//        System.out.println(trap(arr));
//        System.out.println(trap(arr2));
//        System.out.println(trap2(arr3));
        System.out.println(trap2(arr4));
    }

    /**
     * 单调栈法（填平法）：从左向右遇到高位时，则回溯进行最小填平，所有填平的数量累加就是目标值
     */
    public static int trap2(int[] height) {
        int ans = 0;
        int n = height.length;
        if (n <= 0) {
            return ans;
        }
        Deque<Integer> stack = new LinkedList<>();
        //逐步检查，并记录当前检查的高度值下标
        for (int i = 0; i < n; i++) {
            //当前高度值仍上升，出现填平机会
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                //取出前一个高度值下标（这个高度被删除，因为接下来要填平它）
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                //检查再前一个高度值下标，确定可能填平的最大距离和最小高度（注意，这个左坐标不能删除）
                int left = stack.peek();
                //最大距离 = 下标差 - 1
                int curWidth = i - left - 1;
                //最大高度 = 左右高度最小值 - 坑的已有高度
                int curHeight = Math.min(height[left], height[i]) - height[top];
                ans += curWidth * curHeight;
            }
            // 当前高度值下标入栈
            stack.push(i);
        }
        return ans;
    }

    /**
     * 暴力统计法，2n^2复杂度
     *
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        int total = 0;

        // 切面缓存数组
        int[] tmp = new int[height.length];
        int min = 0;
        int max = 0;
        // 整型数据，从min+1开始，一层一层的切到缓存数组中
        for (int i = 0; i < height.length; i++) {
            min = Math.min(min, height[i]);
            max = Math.max(max, height[i]);
            tmp[i] = 0;
        }

        // 最后可以切max刀
        for (int h = min + 1; h <= max; h++) {
            for (int i = 0; i < height.length; i++) {
                // 开始切
                tmp[i] = height[i] - h >= 0 ? 1 : 0;
            }
            int l = 0;
            int r = tmp.length - 1;
            // 先找到左右两边的1
            while (l < r) {
                if (tmp[l] == 0) {
                    l++;
                } else if (tmp[r] == 0) {
                    r--;
                } else {
                    break;
                }
            }
            // 再统计夹在1中的0坑数量
            for (int i = l; i < r; i++) {
                if (tmp[i] == 0) {
                    total++;
                }
            }
        }
        return total;
    }

}
