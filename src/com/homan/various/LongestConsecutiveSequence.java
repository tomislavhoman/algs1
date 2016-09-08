package com.homan.various;

/**
 * http://www.cnblogs.com/jcliBlogger/p/4923745.html
 */
public final class LongestConsecutiveSequence {

    public static void main(final String[] args) {

        System.out.println(findLongestSubsequence(add(1, 3, 2, 4, 5)));
        System.out.println(findLongestSubsequence(add(2, 3, 2, 1)));
    }

    private static int findLongestSubsequence(final Node root) {
        return findMaxNumberOfConsecutiveParents(root, 0) + 1;
    }

    private static int findMaxNumberOfConsecutiveParents(final Node node, final int currentMax) {

        if (node == null) {
            return 0;
        }

        int max = Math.max(node.numberOfConsecutiveParents, currentMax);
        max = Math.max(findMaxNumberOfConsecutiveParents(node.left, currentMax), max);
        max = Math.max(findMaxNumberOfConsecutiveParents(node.right, currentMax), max);
        return max;
    }

    private static Node add(final int... args) {
        Node root = null;
        for (int i : args) {
            root = add(root, i, 0);
        }
        return root;
    }

    private static Node add(final Node parent, final int childValue, final int numberOfConsecutiveParents) {

        if (parent == null) {
            return new Node(childValue, numberOfConsecutiveParents, null, null);
        }

        final int childNoConsecutiveParents = childValue == parent.value + 1 ? parent.numberOfConsecutiveParents + 1 : 0;
        if (parent.value == childValue) {
            return parent;
        } else if (childValue < parent.value) {
            parent.left = add(parent.left, childValue, childNoConsecutiveParents);
        } else if (childValue > parent.value) {
            parent.right = add(parent.right, childValue, childNoConsecutiveParents);
        }

        return parent;
    }

    private static final class Node {

        private final int value;
        private final int numberOfConsecutiveParents;

        private Node left;
        private Node right;

        private Node(int value, int numberOfConsecutiveParents, Node left, Node right) {
            this.value = value;
            this.numberOfConsecutiveParents = numberOfConsecutiveParents;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
