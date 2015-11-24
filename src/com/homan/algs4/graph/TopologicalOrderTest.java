package com.homan.algs4.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class TopologicalOrderTest {

    public static void main(String[] args) {

        In in1 = new In(Inputs.DAG_SMALL);
        TopologicalOrder topologicalOrder = new TopologicalOrder(new Digraph(in1)); //My

        In in2 = new In(Inputs.DAG_SMALL);
        Topological topological = new Topological(new edu.princeton.cs.algs4.Digraph(in2)); //Sedgewick

        for (int v : topologicalOrder.topological()) {
            StdOut.printf("%d -> ", v);
        }
        StdOut.println();

        for (int v : topological.order()) {
            StdOut.printf("%d -> ", v);
        }
        StdOut.println();
    }
}
