package com.homan.algs4.symboltable;

import edu.princeton.cs.algs4.Queue;

public final class RedBlackBST<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private Node root;

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        iterateInorder(root, queue);
        return queue;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        if (!isRed(node.left) && isRed(node.right)) {
            node = rotateLeft(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Value get(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    private Node delete(Node node, Key key) {

        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {

            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }

        return min(node.left);
    }

    private Node deleteMin(Node node) {

        if (node == null) {
            return null;
        }

        if (node.left == null) {
            return node.right;
        }

        node.left = deleteMin(node.left);
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    private void iterateInorder(Node node, Queue<Key> queue) {
        if (node == null) {
            return;
        }

        iterateInorder(node.left, queue);
        queue.enqueue(node.key);
        iterateInorder(node.right, queue);
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }

        return node.color == Node.RED;
    }

    private Node rotateLeft(Node node) {

        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = node.color;
        node.color = Node.RED;
        return temp;
    }

    private Node rotateRight(Node node) {

        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = node.color;
        node.color = Node.RED;
        return temp;
    }

    private void flipColors(Node node) {

        node.left.color = Node.BLACK;
        node.right.color = Node.BLACK;
        node.color = Node.RED;
    }

    private final class Node {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private Key key;
        private Value value;
        private int size;
        private boolean color;

        private Node left;
        private Node right;

        private Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.size = 1;
            this.color = RED;
        }

        @Override
        public String toString() {
            return value.toString() + " - " + (color == RED ? "red" : "black");
        }
    }
}
