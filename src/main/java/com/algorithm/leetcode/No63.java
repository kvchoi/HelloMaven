package com.algorithm.leetcode;

public class No63 {

    public static void main(String[] args) {

    }

    /**
     * //障碍物位置：
     * //if obstacleGrid[i][j] == 1 dp[i][j] = 0
     * //非障碍位置：
     * //if obstacleGrid[i][j] == 0 dp[i][j] = dp[i-1][j] + dp[i][j-1]
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // 需要一个新矩阵保存累积结果，obstacleGrid矩阵用来判断障碍物位置
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];

        //初始化横竖两列为1，如果中间碰到障碍物则终止初始化，其后面的路都无法到达
        //因为机器人是没有办法先往下再往右再往上绕过障碍物的
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        //同理
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        //还是原来的累积公式，从左到右一层层遍历
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // 累计位置为无障碍物
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }


}
