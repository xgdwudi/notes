package com.wudidemiao.study.algorithm.dynamic;

/**
 * @author wudidemiaoa
 * @date 2022/1/22
 * @apiNote
 */
public class KnapsackProblem {
    public static void main(String[] args) {
//        物品的重量
        int[] v = {1, 4, 3};
//        物品的价值
        int[] p = {1500, 3000, 2000};
//        动态规划数组
        int m = 4;  // 背包的容量
        int n = v.length;  // 物品的个数
//        dp[i][j]得含义 表示在前i个物品中能够装入容量为j得背包中得最大价值
        int[][] dp = new int[n + 1][m + 1];

//        初始化第一行和第一列
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (v[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                }
                if (j >= v[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], p[i - 1] + dp[i - 1][j - v[i - 1]]);
                }
            }
        }

        System.out.println(dp[3][3]);
    }
}
