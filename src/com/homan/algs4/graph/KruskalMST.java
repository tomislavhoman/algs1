package com.homan.algs4.graph;

import com.homan.algs4.unionfind.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.Queue;

public class KruskalMST {

    private Queue<Edge> mst;
    private double weight;

    public KruskalMST(EdgeWeightedGraph graph) {

        mst = new LinkedList<>();
//        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(graph.E());
        MinPQ<Edge> priorityQueue = new MinPQ<>();

        for (int v = 0; v < graph.V(); v++) {
            for (Edge edge : graph.adj(v)) {
                priorityQueue.insert(edge);
            }
        }

        WeightedQuickUnionUF union = new WeightedQuickUnionUF(graph.V());
        while (!priorityQueue.isEmpty() && mst.size() < graph.V() - 1) {
            Edge edge = priorityQueue.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (!union.connected(v, w)) {
                mst.add(edge);
                weight += edge.weight();
                union.union(v, w);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
