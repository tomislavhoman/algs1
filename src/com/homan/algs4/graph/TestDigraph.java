package com.homan.algs4.graph;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestDigraph {

    public static void main(String[] args) {

        In in1 = new In(Inputs.DIGRAPH_SMALL);
        In in2 = new In(Inputs.DIGRAPH_SMALL);
        edu.princeton.cs.algs4.Digraph sedgewicks = new edu.princeton.cs.algs4.Digraph(in1);
        Digraph digraph = new Digraph(in2);
        in1.close();
        in2.close();

        StdOut.printf("Sedgewicks - v: %d, e: %d\n", sedgewicks.V(), sedgewicks.E());
        for (int i = 0; i < sedgewicks.V(); i++) {
            for (int w : sedgewicks.adj(i)) {
                StdOut.printf("%d -> %d\n", i, w);
            }
        }
        StdOut.println();

        StdOut.printf("My - v: %d, e: %d\n", digraph.V(), digraph.E());
        for (int i = 0; i < digraph.V(); i++) {
            for (int w : digraph.adj(i)) {
                StdOut.printf("%d -> %d\n", i, w);
            }
        }
        StdOut.println();

        edu.princeton.cs.algs4.Digraph sedwickDi = sedgewicks.reverse();
        StdOut.printf("Sedgewicks reverse - v: %d, e: %d\n", sedwickDi.V(), sedwickDi.E());
        for (int i = 0; i < sedwickDi.V(); i++) {
            for (int w : sedwickDi.adj(i)) {
                StdOut.printf("%d -> %d\n", i, w);
            }
        }
        StdOut.println();

        Digraph myReverse = digraph.reverse();
        StdOut.printf("My reverse - v: %d, e: %d\n", myReverse.V(), myReverse.E());
        for (int i = 0; i < myReverse.V(); i++) {
            for (int w : myReverse.adj(i)) {
                StdOut.printf("%d -> %d\n", i, w);
            }
        }
    }
}
