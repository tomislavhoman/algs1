package com.homan.algs4.priorityqueue;

import java.nio.BufferUnderflowException;

public abstract class BasePQ<T extends Comparable<T>> implements PriorityQueue<T> {

    protected T[] heap;
    private int n = 0;

    @SuppressWarnings("unchecked")
    public BasePQ(int N) {
        this.heap = (T[]) new Comparable[N];
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }

        T first = heap[1];
        exch(1, n);
        heap[n] = null;
        n--;
        sink(1);
        return first;
    }

    @Override
    public void enqueue(T item) {
        n++;
        heap[n] = item;
        swim(n);
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    protected abstract boolean opearation(int i, int j);

    private void exch(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void swim(int i) {
        while (i > 1 && opearation(i, i / 2)) {
            exch(i, i / 2);
            i /= 2;
        }
    }

    private void sink(int i) {

        while (2 * i <= n) {
            int j = 2 * i;
            if (j < n && opearation(j + 1, j)) j++;
            if (!opearation(j, i)) break;
            exch(i, j);
            i = j;
        }
    }
}
