package com.homan.algs4.sort;

public final class SelectionSort extends Sort {

    public static void sort(Comparable[] items) {

        for (int i = 0; i < items.length; i++) {

            int min = i;
            for (int j = i + 1; j < items.length; j++) {
                if (less(items[j], items[min])) {
                    min = j;
                }
            }
            exch(items, i, min);
        }
    }
}
