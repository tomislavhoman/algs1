package com.homan.algs4.symboltable;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/*
Tree:

                5
        3               7
    2       4       6       8
1                               9
 */

public class BstTraversal {

    private TreeNode root;

    public BstTraversal(TreeNode root) {
        this.root = root;
    }

    public void levelOrder() {
        System.out.println("Level order traversal:");

        Queue<TreeNode> queue = new Queue<>();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.dequeue();
            System.out.printf("%d ", node.value);

            if (node.left != null) queue.enqueue(node.left);
            if (node.right != null) queue.enqueue(node.right);
        }
        System.out.println();
        System.out.println();
    }

    public void preOrderRecursive() {
        System.out.println("Pre-order recursive traversal:");
        preOrderRecursive(root);
        System.out.println();
        System.out.println();
    }

    private void preOrderRecursive(TreeNode node) {
        if (node == null) {
            return;
        }

        System.out.printf("%d ", node.value);
        preOrderRecursive(node.left);
        preOrderRecursive(node.right);
    }

    public void inOrderRecursive() {
        System.out.println("In-order recursive traversal:");
        inOrderRecursive(root);
        System.out.println();
        System.out.println();
    }

    private void inOrderRecursive(TreeNode node) {
        if (node == null) {
            return;
        }

        inOrderRecursive(node.left);
        System.out.printf("%d ", node.value);
        inOrderRecursive(node.right);
    }

    public void postOrderRecursive() {
        System.out.println("Post-order recursive traversal:");
        postOrderRecursive(root);
        System.out.println();
        System.out.println();
    }

    private void postOrderRecursive(TreeNode node) {
        if (node == null) {
            return;
        }

        postOrderRecursive(node.left);
        postOrderRecursive(node.right);
        System.out.printf("%d ", node.value);
    }

    public void preOrderIterative() {
        System.out.println("Pre-order iterative traversal:");

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.printf("%d ", node.value);

            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }

        System.out.println();
        System.out.println();
    }

    public void inOrderIterative() {
        System.out.println("In-order iterative traversal:");

        Stack<StackNode> stack = new Stack<>();
        stack.push(new StackNode(root));

        while (!stack.isEmpty()) {
            StackNode node = stack.pop();

            if (node.goingUp) {
                System.out.printf("%d ", node.treeNode.value);
                continue;
            }

            if (node.treeNode.right != null) stack.push(new StackNode(node.treeNode.right));
            node.goingUp = true;
            stack.push(node);
            if (node.treeNode.left != null) stack.push(new StackNode(node.treeNode.left));
        }

        System.out.println();
        System.out.println();
    }

    public void postOrderIterative() {
        System.out.println("Post-order iterative traversal:");

        Stack<StackNode> stack = new Stack<>();
        stack.push(new StackNode(root));

        while (!stack.isEmpty()) {
            StackNode node = stack.pop();

            if (node.goingUp) {
                System.out.printf("%d ", node.treeNode.value);
                continue;
            }

            node.goingUp = true;
            stack.push(node);

            if (node.treeNode.right != null) stack.push(new StackNode(node.treeNode.right));
            if (node.treeNode.left != null) stack.push(new StackNode(node.treeNode.left));
        }

        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode root = null;
        root = put(root, 5);
        root = put(root, 3);
        root = put(root, 7);
        root = put(root, 2);
        root = put(root, 4);
        root = put(root, 1);
        root = put(root, 6);
        root = put(root, 8);
        root = put(root, 9);

        BstTraversal traversal = new BstTraversal(root);
        traversal.levelOrder();
        traversal.preOrderRecursive();
        traversal.inOrderRecursive();
        traversal.postOrderRecursive();
        traversal.preOrderIterative();
        traversal.inOrderIterative();
        traversal.postOrderIterative();
    }

    private static TreeNode put(TreeNode node, int value) {

        if (node == null) {
            node = new TreeNode();
            node.value = value;
            return node;
        }

        if (value < node.value) {
            node.left = put(node.left, value);
        } else if (value > node.value) {
            node.right = put(node.right, value);
        } else {
            node.value = value;
        }

        return node;
    }

    private static final class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;
    }

    private static final class StackNode {
        private TreeNode treeNode;
        private boolean goingUp = false;

        private StackNode(TreeNode treeNode) {
            this.treeNode = treeNode;
        }
    }
}
