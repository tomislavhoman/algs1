package com.homan.algs4.sort;

public final class MergeSortBottomUp extends Sort {

    public static void sort(Comparable[] a) {

        Comparable[] aux = new Comparable[a.length];

        int delta = 1;
        while (delta < a.length) {
            for (int lo = 0; lo < a.length; lo += 2 * delta) {
                merge(a, aux, lo, lo + delta - 1, Math.min(lo + 2 * delta - 1, a.length - 1));
            }
            delta *= 2;
        }
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int i = lo, j = mid + 1, k = lo; k <= hi; k++) {

            if (i > mid) {
                a[k] = aux[j];
                j++;
            } else if (j > hi) {
                a[k] = aux[i];
                i++;
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j];
                j++;
            } else {
                a[k] = aux[i];
                i++;
            }
        }
    }
}
