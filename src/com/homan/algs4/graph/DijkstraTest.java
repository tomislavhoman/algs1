package com.homan.algs4.graph;

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public class DijkstraTest {

    public static void main(String[] args) {
        In in = new In(Inputs.WEIGHTED_DIGRAPH);
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(in);

        Dijkstra dijkstra = new Dijkstra(graph, 0); //My implementation
        DijkstraSP dijkstraSP = new DijkstraSP(graph, 0); //Sedgewick

        // Codility style Java only, no care for style
        DijkstraJavaOnly dijkstraJavaOnly = new DijkstraJavaOnly(Inputs.WEIGHTED_DIGRAPH, 0);

        StdOut.printf("My implementation:\n");
        for (int v = 0; v < graph.V(); v++) {
            if (dijkstra.hasPathTo(v)) {
                StdOut.printf("Dist to %d: %f.| ", v, dijkstra.distTo(v));
                for (DirectedEdge edge : dijkstra.pathTo(v)) {
                    StdOut.printf("%d -> %d, ", edge.from(), edge.to());
                }
                StdOut.println();
            } else {
                StdOut.printf("%s is unreachable\n", v);
            }
        }
        StdOut.println();

        StdOut.printf("Sedgewicks implementation:\n");
        for (int v = 0; v < graph.V(); v++) {
            if (dijkstraSP.hasPathTo(v)) {
                StdOut.printf("Dist to %d: %f.| ", v, dijkstraSP.distTo(v));
                for (DirectedEdge edge : dijkstraSP.pathTo(v)) {
                    StdOut.printf("%d -> %d, ", edge.from(), edge.to());
                }
                StdOut.println();
            } else {
                StdOut.printf("%s is unreachable\n", v);
            }
        }
        StdOut.println();

        StdOut.printf("Sedgewicks implementation:\n");
        for (int v = 0; v < graph.V(); v++) {
            if (dijkstraJavaOnly.hasPathTo(v)) {
                StdOut.printf("Dist to %d: %f.| ", v, dijkstraJavaOnly.distTo(v));
                Stack<Integer> path = dijkstraJavaOnly.pathTo(v);
                while (!path.isEmpty()) {
                    StdOut.printf("%d -> ", path.pop());
                }
                StdOut.println();
            } else {
                StdOut.printf("%s is unreachable\n", v);
            }
        }

        StdOut.println();
    }
}
