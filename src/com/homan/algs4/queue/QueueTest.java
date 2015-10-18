package com.homan.algs4.queue;

public class QueueTest {

    public static void main(String[] args) {

        Queue<Integer> queue1 = new LinkedListQueue<>();
        Queue<Integer> queue2 = new CyclicArrayQueue<>(10);

        for (int i = 0; i < 10; i++) {
            queue1.enqueue(i);
            queue2.enqueue(i);
        }

        while (!queue1.isEmpty()) {
            System.out.println(queue1.dequeue());
        }

        System.out.println();

        while (!queue2.isEmpty()) {
            System.out.println(queue2.dequeue());
        }
    }
}
