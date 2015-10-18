package com.homan.algs4.priorityqueue;

import java.util.Random;

public class PriorityQueueTest {

    public static void main(String[] args) {

        final BasePQ<Integer> maxPQ = new MaxPQ<>(32);
        final BasePQ<Integer> minPQ = new MinPQ<>(32);
        final Random random = new Random();

        for (int i = 0; i < 20; i++) {
            final int el = random.nextInt(20);
            maxPQ.enqueue(el);
            minPQ.enqueue(el);
        }

        System.out.print("MaxPQ: ");
        while (!maxPQ.isEmpty()) {
            System.out.print(maxPQ.dequeue() + " ");
        }
        System.out.println();
        System.out.print("MinPQ: ");
        while (!minPQ.isEmpty()) {
            System.out.print(minPQ.dequeue() + " ");
        }
        System.out.println();
    }
}
