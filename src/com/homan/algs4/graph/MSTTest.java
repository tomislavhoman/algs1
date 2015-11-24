package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class MSTTest {

    public static void main(String[] args) {

        In in = new In(Inputs.WEIGHTED_SMALL);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(in);

        KruskalMST mst1 = new KruskalMST(graph); //My
        edu.princeton.cs.algs4.KruskalMST mst2 = new edu.princeton.cs.algs4.KruskalMST(graph); //Sedgewick
        PrimMST mst3 = new PrimMST(graph); //My
        edu.princeton.cs.algs4.PrimMST mst4 = new edu.princeton.cs.algs4.PrimMST(graph); //Sedgewick

        StdOut.printf("My Kruskal implementation. Weight: %f\n", mst1.weight());
        for (Edge edge : mst1.edges()) {
            int v = edge.either();
            int w = edge.other(v);
            StdOut.printf("%d -> %d\n", v, w);
        }
        StdOut.println();

        StdOut.printf("Sedgewick Kruskal implementation. Weight: %f\n", mst2.weight());
        for (Edge edge : mst2.edges()) {
            int v = edge.either();
            int w = edge.other(v);
            StdOut.printf("%d -> %d\n", v, w);
        }
        StdOut.println();

        StdOut.printf("My Prim implementation. Weight: %f\n", mst3.weight());
        for (Edge edge : mst3.edges()) {
            int v = edge.either();
            int w = edge.other(v);
            StdOut.printf("%d -> %d\n", v, w);
        }
        StdOut.println();

        StdOut.printf("Sedgewick Prim implementation. Weight: %f\n", mst4.weight());
        for (Edge edge : mst4.edges()) {
            int v = edge.either();
            int w = edge.other(v);
            StdOut.printf("%d -> %d\n", v, w);
        }
        StdOut.println();
    }
}
