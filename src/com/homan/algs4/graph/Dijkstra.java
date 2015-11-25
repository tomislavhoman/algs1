package com.homan.algs4.graph;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class Dijkstra {

    private static final double INF = Double.MAX_VALUE;

    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public Dijkstra(EdgeWeightedDigraph graph, int s) {

        edgeTo = new DirectedEdge[graph.V()];

        distTo = new double[graph.V()];
        distTo[s] = 0.0;
        for (int i = 0; i < graph.V(); i++) {
            if (i == s) {
                continue;
            }

            distTo[i] = INF;
        }

        IndexMinPQ<Double> queue = new IndexMinPQ<>(graph.V());
        queue.insert(s, 0.0);

        while (!queue.isEmpty()) {
            int v = queue.delMin();
            for (DirectedEdge edge : graph.adj(v)) {
                int w = edge.to();

                if (distTo[v] + edge.weight() < distTo[w]) {
                    edgeTo[w] = edge;
                    distTo[w] = distTo[v] + edge.weight();
                    if (queue.contains(w)) {
                        queue.decreaseKey(w, distTo[w]);
                    } else {
                        queue.insert(w, distTo[w]);
                    }
                }
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo(v) < INF;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> path = new Stack<>();

        DirectedEdge edge = edgeTo[v];
        while (edge != null) {
            path.push(edge);
            edge = edgeTo[edge.from()];
        }

        return path;
    }
}
