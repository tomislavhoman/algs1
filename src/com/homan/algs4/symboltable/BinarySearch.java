package com.homan.algs4.symboltable;

import java.util.Arrays;

public final class BinarySearch<T extends Comparable<T>> {

    private final T[] items;

    @SuppressWarnings("unchecked")
    public BinarySearch(T[] items) {

        this.items = (T[]) new Comparable[items.length];
        System.arraycopy(items, 0, this.items, 0, items.length);
        Arrays.sort(this.items);
    }

    public T find(T key) {

        int lo = 0;
        int hi = items.length - 1;
        while (hi >= lo) {
            int mid = lo + (hi - lo) / 2;
            T current = items[mid];
            int cmp = key.compareTo(current);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return current;
            }
        }

        return null;
    }
}
