package com.homan.algs4.priorityqueue;

public interface PriorityQueue<T extends Comparable<T>> {

    T dequeue();

    void enqueue(T item);

    boolean isEmpty();
}
