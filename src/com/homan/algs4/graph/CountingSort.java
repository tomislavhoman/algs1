package com.homan.algs4.graph;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class CountingSort {

    public static void main(String[] args) {

        int maxNumber = 10;
        int n = 20;

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(maxNumber); // 0 - 99
        }

        StdOut.println(Arrays.toString(a));

        int count[] = new int[maxNumber + 1];
        for (int i = 0; i < a.length; i++) {
            count[a[i] + 1]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] = count[i - 1] + count[i];
        }

        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[count[a[i]]] = a[i];
            count[a[i]]++;
        }

        for (int i = 0; i < a.length; i++) {
            a[i] = b[i];
        }

        StdOut.println(Arrays.toString(a));
    }
}
