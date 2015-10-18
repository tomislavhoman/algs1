package com.homan.algs4.sort;

public class SortTest {

    public static void main(String[] args) {

        Integer[] items1 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};
        Integer[] items2 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};
        Integer[] items3 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8, 6, 1, 55, 32, 13, 45, 73, 96, 46, 36, 36, 73, 56, 34, 11, 22, 77, 32, 90};
        Integer[] items4 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] items5 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};
        Integer[] items6 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};
        Integer[] items7 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};
        Integer[] items8 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};
        Integer[] items9 = new Integer[]{3, 4, 6, 2, 4, 5, 1, 7, 9, 8};

        SelectionSort.sort(items1);
        InsertionSort.sort(items2);
        ShellSort.sort(items3);
        KnuthShuffle.shuffle(items4);
        MergeSort.sort(items5);
        MergeSort2.sort(items6);
        MergeSortBottomUp.sort(items7);
        QuickSort.sort(items8);
        HeapSort.sort(items9);

        printItems(SelectionSort.class, items1);
        printItems(InsertionSort.class, items2);
        printItems(ShellSort.class, items3);
        printItems(KnuthShuffle.class, items4);
        printItems(MergeSort.class, items5);
        printItems(MergeSort2.class, items6);
        printItems(MergeSortBottomUp.class, items7);
        printItems(QuickSort.class, items8);
        printItems(HeapSort.class, items9);
    }

    private static void printItems(Class clazz, Comparable[] items) {
        System.out.print(clazz.getSimpleName() + ": ");
        for (Comparable el : items) {
            System.out.print(el + " ");
        }
        System.out.println();
    }
}
