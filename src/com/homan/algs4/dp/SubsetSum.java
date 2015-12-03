package com.homan.algs4.dp;

import java.util.Arrays;

/*
In a variant of the Knapsack problem, all the items in the vault are gold bars. The value of a gold
bar is directly proportional to its weight. Therefore, in order to make the most amount of money,
you must fill your knapsack up to its full capacity of S pounds. Can you find a subset of the gold
bars whose weights add up to exactly S?
 */
public class SubsetSum {

    private final int S;
    private final int[] a;

    public SubsetSum(int s, int[] a) {
        S = s;
        this.a = a;
    }

    private boolean result() {

        int[][] dp = new int[S + 1][a.length + 1];
        for (int i = 0; i < S + 1; i++) {
            dp[i][a.length] = 0;
        }

        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = 0; j < S + 1; j++) {
                if (j - a[i] >= 0) {
                    dp[j][i] = Math.max(dp[j][i + 1], a[i] + dp[j - a[i]][i + 1]);

                } else {
                    dp[j][i] = dp[j][i + 1];
                }
            }
        }

        return dp[S][0] == S;
    }

    public static void main(String[] args) {
        int S = 39;
        int a[] = new int[]{1, 5, 3, 7, 10, 11}; //3, 7, 10 for 20
        // 1..37 / 2, 35

        SubsetSum subsetSum = new SubsetSum(S, a);
        System.out.println(subsetSum.result() ? "There is a subset" : "There isn't any subset");
    }

}
