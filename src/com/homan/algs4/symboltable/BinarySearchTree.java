package com.homan.algs4.symboltable;

import edu.princeton.cs.algs4.Queue;

public final class BinarySearchTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private Node root = null;

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
            return new Node(key, value, 1);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
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
            node = min(node.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int size(Node node) {
        return node != null ? node.size : 0;
    }

    private void iterateInorder(Node node, Queue<Key> queue) {
        if (node == null) {
            return;
        }

        iterateInorder(node.left, queue);
        queue.enqueue(node.key);
        iterateInorder(node.right, queue);
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }

        return min(node.left);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }

        node.left = deleteMin(node.left);
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private final class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int size = 0;

        private Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }
}
