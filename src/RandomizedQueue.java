import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_SIZE = 2;

    private int current = 0;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[DEFAULT_SIZE];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return current == 0;
    }

    // return the number of items on the queue
    public int size() {
        return current;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (current == items.length) {
            expandArray();
        }

        items[current++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        current--;
        final int randomIndex = StdRandom.uniform(0, current + 1);
        swap(current, randomIndex);
        final Item result = items[current];
        items[current] = null; //loitering prevention


        if (current > DEFAULT_SIZE && current <= items.length / 4) {
            contractArray();
        }

        return result;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        return items[StdRandom.uniform(0, current)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void expandArray() {
        Item[] newArray = (Item[]) new Object[items.length * 2];
        for (int i = 0; i < items.length; i++) {
            newArray[i] = items[i];
        }

        this.items = newArray;
    }

    private void contractArray() {
        Item[] newArray = (Item[]) new Object[items.length / 2];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = items[i];
        }

        this.items = newArray;
    }

    private void swap(int a, int b) {
        Item temp = items[a];
        items[a] = items[b];
        items[b] = temp;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int curr = 0;
        private Item[] copy;

        public RandomizedQueueIterator() {
            this.copy = (Item[]) new Object[current];
            for (int i = 0; i < current; i++) {
                copy[i] = items[i];
            }
            StdRandom.shuffle(copy);
        }

        @Override
        public boolean hasNext() {
            return curr < copy.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return copy[curr++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        System.out.println(queue.isEmpty());
        queue.enqueue(1);
        System.out.println(queue.isEmpty());
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        for (Integer item : queue) {
            System.out.print(item + ", ");
        }
        System.out.println();
        System.out.print(queue.dequeue() + ", ");
        System.out.print(queue.dequeue() + ", ");
        System.out.print(queue.dequeue() + ", ");
        System.out.print(queue.dequeue() + ", ");
        System.out.print(queue.dequeue() + ", ");
        System.out.print(queue.dequeue() + ", ");
        System.out.println();
        System.out.println(queue.isEmpty());
    }
}
