package com.homan.algs4.stack;

public final class ArrayStack<T> implements Stack<T> {

    private static final int EMPTY = -1;
    private T[] items;
    private int current = EMPTY;

    @SuppressWarnings("unchecked")
    public ArrayStack(int N) {
        items = (T[]) new Object[N];
    }

    @Override
    public void push(T item) {
        if (current == items.length - 1) {
            throw new StackOverflowError();
        }

        items[++current] = item;
    }

    @Override
    public T pop() {
        if (current == EMPTY) {
            throw new RuntimeException("Stack is empty");
        }

        T item = items[current];
        items[current] = null;
        current--;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return current == EMPTY;
    }
}
