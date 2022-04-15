package com.algorithm.leetcode;

import java.util.Arrays;

public class No62 {

    public static void main(String[] args) {
        System.out.println(uniquePaths3(7, 3));
    }

    // 递归累加可能步数
    // 测试用例【51, 9】，会导致超出时间限制
    public static int uniquePaths(int m, int n) {
        // 当处于第【1，0】格时，或【0，1】格时，就差最后一步了
        if (m == 1 || n == 1) {
            return 1;
        }
        // 处于第【m，n】格可以产生两步，一个是向右【m, n-1】，一个是向下【m-1, n】
        return uniquePaths(m - 1, n) + uniquePaths(m, n - 1);
    }

    /**
     * 动态规划：
     * <p>
     * 将m和n组合成一个m*n的矩阵，并从终点最近的格子开始计算可选路径数量，可以发现有累加规则。
     * 然后，把矩阵倒过来看，是否可以使用程序进行自动累加出这个矩阵。
     * <p>
     * [
     * [0],[1],[1],[1],[1],[1],[1]...
     * [1],[2],[3],[4],[5],[6],[7]...
     * [1],[3],[6],[10],[15],[21],[28]...
     * ....
     * ]
     * 从上可以看出f(m,n)=f(m-1,n)+f(m,n-1)累加公式
     *
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths2(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        // 初始化横竖两个边，都是只有一条路径可选
        int[][] arr = new int[m][n];
        for (int i = 0; i < m; ++i) {
            arr[i][0] = 1;
        }
        for (int j = 0; j < n; ++j) {
            arr[0][j] = 1;
        }
        // 计算其它位置的可选路径时，依赖于它前方横竖各一步的可选路径，所以直接求和即可
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                arr[r][c] = arr[r - 1][c] + arr[r][c - 1];
            }
        }
        return arr[m - 1][n - 1];
    }

    //动态规划（优化空间），只需要两行数组，轮流使用即可
    public static int uniquePaths3(int m, int n) {
        int[] s0 = new int[n];
        int[] s1 = new int[n];

        Arrays.fill(s0, 1);
        Arrays.fill(s1, 1);

        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                s1[c] = s1[c - 1] + s0[c];
            }
            // 使用对拷，不用复制生成新数组，更加高效
            System.arraycopy(s1, 0, s0, 0, n);
        }
        return s1[n - 1];
    }

    //动态规划（优化空间），只需要一行数组，因为s1复制到s0的数组元素，就存在于s1本身呀，直接加自己就得了
    public static int uniquePaths4(int m, int n) {
        int[] s1 = new int[n];
        Arrays.fill(s1, 1);

        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                s1[c] = s1[c - 1] + s1[c];
            }
        }
        return s1[n - 1];
    }


}
