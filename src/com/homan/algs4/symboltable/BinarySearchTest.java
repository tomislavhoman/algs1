package com.homan.algs4.symboltable;

public class BinarySearchTest {

    public static void main(String[] args) {

        Integer[] a = new Integer[]{3, 9, 10, 1, 5, 6, 2, 11, 9, 7, 3, 7, 4, 6};
        BinarySearch<Integer> binarySearch = new BinarySearch<>(a);

        System.out.println(binarySearch.find(9));
        System.out.println(binarySearch.find(3));
        System.out.println(binarySearch.find(6));
        System.out.println(binarySearch.find(2));
        System.out.println(binarySearch.find(11));
        System.out.println(binarySearch.find(1));
        System.out.println(binarySearch.find(55));
        System.out.println(binarySearch.find(-4));
    }
}
