package com.homan.algs4.queue;

public final class LinkedListQueue<T> implements Queue<T> {

    private Node head = null;
    private Node tail = null;

    @Override
    public void enqueue(T item) {
        Node newNode = new Node();
        newNode.value = item;
        newNode.next = null;

        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;

        if (head == null) {
            head = newNode;
        }
    }

    @Override
    public T dequeue() {
        if (head == null) {
            throw new RuntimeException("Queue empty");
        }

        T item = head.value;
        head = head.next;

        if (isEmpty()) {
            tail = null;
        }

        return item;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private class Node {
        T value;

        Node next;
    }
}
