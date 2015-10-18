package com.homan.algs4.stack;

public final class LinkedListStack<T> implements Stack<T> {

    private Node head = null;

    @Override
    public void push(T item) {
        Node node = new Node();
        node.value = item;
        node.next = head;
        head = node;

    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack empty");
        }

        T item = head.value;
        head = head.next;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private class Node {

        private T value;

        private Node next;
    }
}
