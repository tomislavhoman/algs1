import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null; //Convention
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        nullCheck(item);
        size++;
        if (isEmpty()) {
            addFirstItem(item);
            return;
        }

        Node newNode = new Node(item);
        newNode.prev = null;
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    // add the item to the end
    public void addLast(Item item) {
        nullCheck(item);
        size++;
        if (isEmpty()) {
            addFirstItem(item);
            return;
        }

        Node newNode = new Node(item);
        newNode.prev = tail;
        newNode.next = null;
        tail.next = newNode;
        tail = newNode;
    }

    private void nullCheck(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;
        if (head == tail) {
            return returnOnlyItem();
        }

        Item result = head.item;
        head = head.next;
        head.prev = null;
        return result;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        size--;
        if (head == tail) {
            return returnOnlyItem();
        }

        Item result = tail.item;
        tail = tail.prev;
        tail.next = null;
        return result;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new FirstToLastIterator();
    }

    private void addFirstItem(Item item) {
        Node newNode = new Node(item);
        newNode.prev = null;
        newNode.next = null;
        head = newNode;
        tail = newNode;
    }

    private Item returnOnlyItem() {
        Item result = head.item; //convention
        head = null;
        tail = null;
        return result;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class FirstToLastIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item next = current.item;
            current = current.next;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<>();
        for (Integer item : deq) {
            System.out.print(item + ", ");
        }
        deq.addFirst(1);
        deq.addLast(2);
        deq.addLast(3);
        deq.addFirst(2);
        deq.addFirst(3);
        System.out.println("Size: " + deq.size());
        for (Integer item : deq) {
            System.out.print(item + ", ");
        }
    }
}
