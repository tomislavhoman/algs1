package com.homan.algs4.unionfind;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public final class QuickFindTest {

    public static void main(String[] args) {
        int N = StdIn.readInt();
//        UF uf = new QuickFindUF(N);
//        UF uf = new QuickUnionUF(N);
        UF uf = new WeightedQuickUnionUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }

    }
}
