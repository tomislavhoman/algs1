package com.homan.algs4.sort;

public final class InsertionSort extends Sort {

    public static void sort(Comparable[] items) {

        for (int i = 1; i < items.length; i++) {
            for (int j = i; j > 0; j--) {
                if (less(items[j], items[j - 1])) {
                    exch(items, j, j - 1);
                }
            }
        }
    }
}
