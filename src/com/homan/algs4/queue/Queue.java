package com.homan.algs4.queue;

public interface Queue<T> {

    void enqueue(T item);

    T dequeue();

    boolean isEmpty();
}
