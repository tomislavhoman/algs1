package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class PrimMST {

    private Queue<Edge> mst;
    private double weight;

    public PrimMST(EdgeWeightedGraph graph) {
        mst = new Queue<>();

        boolean[] inMst = new boolean[graph.V()];
        MinPQ<Edge> priorityQueue = new MinPQ<>();
        enqueueVertexEdges(0, graph, priorityQueue);
        inMst[0] = true;

        while (!priorityQueue.isEmpty() && mst.size() < graph.V() - 1) {
            Edge edge = priorityQueue.delMin();
            int v = edge.either();
            if (!inMst[v]) {
                v = edge.other(v);
            }
            int w = edge.other(v);

            if (!inMst[w]) {
                mst.enqueue(edge);
                inMst[w] = true;
                weight += edge.weight();
                enqueueVertexEdges(w, graph, priorityQueue);
            }
        }
    }

    private void enqueueVertexEdges(int v, EdgeWeightedGraph graph, MinPQ<Edge> queue) {
        for (Edge edge : graph.adj(v)) {
            queue.insert(edge);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
