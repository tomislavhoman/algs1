package com.homan.algs4.priorityqueue;

public final class MaxPQ<T extends Comparable<T>> extends BasePQ<T> {

    public MaxPQ(int N) {
        super(N);
    }

    @Override
    protected boolean opearation(int i, int j) {
        return heap[i].compareTo(heap[j]) > 0;
    }
}
