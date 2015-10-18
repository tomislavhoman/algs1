package com.homan.algs4.sort;

public final class ShellSort extends Sort {

    public static void sort(Comparable[] items) {

        int step = 1;
        while (step < items.length) {
            step = step * 2 + 1;
        }
        step = step / 2;

        while (step >= 1) {
            for (int i = step; i < items.length; i++) {
                for (int j = i; j >= step; j -= step) {
                    if (less(items[j], items[j - step])) {
                        exch(items, j, j - step);
                    }
                }
            }
            step = step / 2;
        }
    }
}
