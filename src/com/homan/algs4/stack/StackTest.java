package com.homan.algs4.stack;

public class StackTest {

    public static void main(String[] args) {

        final Stack<Integer> stack1 = new LinkedListStack<>();
        final Stack<Integer> stack2 = new ArrayStack<>(10);

        for (int i = 0; i < 10; i++) {
            stack1.push(i);
            stack2.push(i);
        }

        while (!stack1.isEmpty()) {
            System.out.println(stack1.pop());
        }

        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop());
        }
    }
}
