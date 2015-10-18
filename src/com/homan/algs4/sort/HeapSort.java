package com.homan.algs4.sort;

public final class HeapSort extends Sort {

    private static int n;

    public static void sort(Comparable[] a) {

        n = a.length;
        for (int i = a.length / 2; i >= 1; i--) {
            sink(a, i);
        }

        while (n > 0) {
            exch(a, 1, n--);
            sink(a, 1);
        }
    }

    private static void sink(Comparable[] a, int i) {

        while (2 * i <= n) {
            int j = 2 * i;
            if (j < n && less(a, j, j + 1)) j++;
            if (!less(a, i, j)) break;
            exch(a, i, j);
            i = j;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return Sort.less(a[i - 1], a[j - 1]);
    }

    protected static void exch(Comparable[] a, int i, int j) {
        Sort.exch(a, i - 1, j - 1);
    }
}
