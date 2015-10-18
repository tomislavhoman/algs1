package com.homan.algs4.sort;

public abstract class Sort {

    protected static boolean less(Comparable left, Comparable right) {
        return left.compareTo(right) < 0;
    }

    protected static void exch(Comparable[] items, int i, int j) {
        Comparable item = items[i];
        items[i] = items[j];
        items[j] = item;
    }
}
