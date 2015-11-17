package com.homan.algs4.sort;

import edu.princeton.cs.algs4.StdOut;

public final class Inverse {

    private static final int NUMBER_OF_ELEMENTS = 10;

    public static void main(String[] args) {

        Integer[] itemsBrute = new Integer[NUMBER_OF_ELEMENTS];
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            itemsBrute[i] = i;
        }

        KnuthShuffle.shuffle(itemsBrute);

//        Integer[] itemsBrute = {7, 3, 5, 0, 1, 8, 4, 2, 9, 6}; // 19
        Integer[] itemsDivideAndConquer = new Integer[NUMBER_OF_ELEMENTS];
        System.arraycopy(itemsBrute, 0, itemsDivideAndConquer, 0, NUMBER_OF_ELEMENTS);

        int numberOfInversesBrute = brute(itemsBrute);
        int numberOfInversesDAndC = divideAndConquer(itemsDivideAndConquer);
        printArray(itemsBrute);
        StdOut.printf("Brute: %d\n", numberOfInversesBrute);
        StdOut.printf("Divide and conquer: %d\n", numberOfInversesDAndC);
    }

    private static void printArray(Integer[] itemsBrute) {
        for (int i : itemsBrute) {
            StdOut.printf("%d, ", i);
        }
        StdOut.println();
    }

    private static int brute(Integer[] items) {

        int numberOfInverses = 0;
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items.length; j++) {
                if (i == j) {
                    continue;
                }

                if (i < j && items[i] > items[j]) {
                    numberOfInverses++;
                }
            }
        }
        return numberOfInverses;
    }

    private static int divideAndConquer(Integer[] items) {

        Integer[] aux = new Integer[items.length];
        return divideAndConquer(items, aux, 0, items.length - 1);
    }

    private static int divideAndConquer(Integer[] items, Integer[] aux, int lo, int hi) {

        if (lo >= hi) {
            return 0;
        }

        int mid = lo + (hi - lo) / 2;
        int left = divideAndConquer(items, aux, 0, mid);
        int right = divideAndConquer(items, aux, mid + 1, hi);

        for (int i = lo; i <= hi; i++) {
            aux[i] = items[i];
        }

        int inverses = 0;
        for (int i = lo, j = mid + 1, k = lo; k <= hi; k++) {

            if (i > mid) {
                items[k] = aux[j++];
            } else if (j > hi) {
                items[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                items[k] = aux[j++];
                inverses += (mid + 1 - i);
            } else {
                items[k] = aux[i++];
            }
        }

        return left + right + inverses;
    }
}
