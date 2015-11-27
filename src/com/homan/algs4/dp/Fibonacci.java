package com.homan.algs4.dp;

public class Fibonacci {

    private static int[] memo1;
    private static int[] memo2;

    public static void main(String[] args) {

        int n = 46;
        memo1 = new int[n + 1];
        memo2 = new int[n + 1];

        long start = System.currentTimeMillis();
        int f = fibonacciRecursive(n);
        long time = System.currentTimeMillis() - start;
        System.out.printf("Recursive: F = %d, time = %d\n", f, time);

        start = System.currentTimeMillis();
        f = fibonacciDPRecursive(n);
        time = System.currentTimeMillis() - start;
        System.out.printf("DP recursive: F = %d, time = %d\n", f, time);

        start = System.currentTimeMillis();
        f = fibonacciDPBottomUp(n);
        time = System.currentTimeMillis() - start;
        System.out.printf("DP Bottom up: F = %d, time = %d\n", f, time);
    }

    private static int fibonacciDPBottomUp(int n) {

        for (int i = 1; i <= n; i++) {
            if (i <= 2) {
                memo2[i] = 1;
                continue;
            }

            memo2[i] = memo2[i - 1] + memo2[i - 2];
        }

        return memo2[n];
    }

    private static int fibonacciDPRecursive(int n) {
        if (memo1[n] > 0) {
            return memo1[n];
        }

        if (n <= 2) {
            return 1;
        }

        int f = fibonacciDPRecursive(n - 1) + fibonacciDPRecursive(n - 2);
        memo1[n] = f;
        return f;
    }

    private static int fibonacciRecursive(int n) {
        if (n <= 2) {
            return 1;
        }

        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
}
