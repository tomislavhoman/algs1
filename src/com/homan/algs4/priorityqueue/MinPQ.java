package com.homan.algs4.priorityqueue;

public final class MinPQ<T extends Comparable<T>> extends BasePQ<T> {

    public MinPQ(int N) {
        super(N);
    }

    @Override
    protected boolean opearation(int i, int j) {
        return heap[i].compareTo(heap[j]) < 0;
    }
}
