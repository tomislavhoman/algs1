package com.homan.algs4.sort;

public final class QuickSort extends Sort {

    public static void sort(Comparable[] a) {
        KnuthShuffle.shuffle(a);
        qsort(a, 0, a.length - 1);
    }

    private static void qsort(Comparable[] a, int lo, int hi) {

        if (lo >= hi) {
            return;
        }

        int pivot = partition(a, lo, hi);
        qsort(a, lo, pivot - 1);
        qsort(a, pivot + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {

        int i = lo + 1;
        int j = hi;
        while (i < j) {
            while (less(a[i], a[lo]) && i < hi) {
                i++;
            }
            while (less(a[lo], a[j]) && j > lo) {
                j--;
            }
            if (i <= j) {
                exch(a, i, j);
            }
        }
        exch(a, lo, j);
        return j;
    }
}
