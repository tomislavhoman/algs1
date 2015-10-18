package com.homan.algs4.queue;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

public final class CyclicArrayQueue<T> implements Queue<T> {

    private T[] items;
    private int head = 0;
    private int tail = 0;

    @SuppressWarnings("unchecked")
    public CyclicArrayQueue(int N) {
        items = (T[]) new Object[N];
    }

    @Override
    public void enqueue(T item) {
        if (tail == head - 1) {
            throw new BufferOverflowException();
        }

        items[tail] = item;
        tail = (tail + 1) % items.length;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }

        T item = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return head == tail && items[head] == null;
    }
}
