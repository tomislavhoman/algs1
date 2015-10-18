package com.homan.algs4.sort;

public final class MergeSort extends Sort {

    public static void sort(Comparable[] items) {
        Comparable[] aux = new Comparable[items.length];
        sort(items, aux, 0, items.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {

        if (lo >= hi) {
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }

        for (int i = lo, j = mid + 1, k = lo; k <= hi; k++) {

            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }
}
