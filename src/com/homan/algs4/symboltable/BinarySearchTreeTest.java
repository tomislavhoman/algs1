package com.homan.algs4.symboltable;

public class BinarySearchTreeTest {

    public static void main(String[] args) {


//        SymbolTable<Integer, String> bst = new BinarySearchTree<>();
        SymbolTable<Integer, String> bst = new RedBlackBST<>();
        printEmpty(bst);

        bst.put(5, "5");
        bst.put(10, "10");
        bst.put(1, "1");
        bst.put(4, "4");
        bst.put(3, "3");
        bst.put(8, "8");
        bst.put(6, "6");
        bst.put(9, "9");
        bst.put(11, "11");
        bst.put(3, "333"); //should override

        //Has keys
        System.out.println(bst.get(5));
        System.out.println(bst.get(10));
        System.out.println(bst.get(1));
        System.out.println(bst.get(4));
        System.out.println(bst.get(3)); //should be 333
        System.out.println(bst.get(8));
        System.out.println(bst.get(6));
        System.out.println(bst.get(9));
        System.out.println(bst.get(11));
        System.out.println(bst.get(0));
        System.out.println(bst.get(55));
        printEmpty(bst);
        printTree(bst);
        printSize(bst);

        bst.delete(11);
        printTree(bst);
        printSize(bst);

        bst.delete(8);
        printTree(bst);
        printSize(bst);

        bst.delete(5);
        bst.delete(10);
        bst.delete(1);
        bst.delete(4);
        bst.delete(8); //shouldn't break
        bst.delete(6);
        bst.delete(9);
        bst.delete(11); //shouldn't break
        printTree(bst);
        printSize(bst);

        bst.delete(3);
        printTree(bst);
        printEmpty(bst);
        printSize(bst);
    }

    private static void printSize(SymbolTable<Integer, String> bst) {
        System.out.println("Size: " + bst.size());
    }

    private static void printTree(SymbolTable<Integer, String> bst) {
        System.out.print("Tree: ");
        for (Integer key : bst.keys()) {
            System.out.print(bst.get(key) + ", ");
        }
        System.out.println();
    }

    private static void printEmpty(SymbolTable bst) {
        System.out.println("Empty: " + bst.isEmpty());
    }
}
