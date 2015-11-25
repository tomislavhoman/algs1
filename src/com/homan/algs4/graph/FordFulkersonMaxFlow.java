package com.homan.algs4.graph;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class FordFulkersonMaxFlow {

    private double maxFlow = 0.0;
    private FlowEdge[] edgeTo;

    public FordFulkersonMaxFlow(FlowNetwork flowNetwork, int s, int t) {

        while (hasAugmentingPath(flowNetwork, s, t)) {
            double bottle = caluclateBottle(s, t);
            increaseFlowAlongAugmentingPath(bottle, s, t);
            maxFlow += bottle;
        }
    }

    private void increaseFlowAlongAugmentingPath(double bottle, int s, int t) {
        int v = t;
        while (v != s) {
            FlowEdge edge = edgeTo[v];
            edge.addResidualFlowTo(v, bottle);
            v = edgeTo[v].other(v);
        }
    }

    private double caluclateBottle(int s, int t) {

        double bottle = Double.MAX_VALUE;

        int v = t;
        while (v != s) {
            FlowEdge edge = edgeTo[v];
            bottle = Math.min(bottle, edge.residualCapacityTo(v));
            v = edge.other(v);
        }

        return bottle;
    }

    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int s, int t) {

        edgeTo = new FlowEdge[flowNetwork.V()];
        boolean[] marked = new boolean[flowNetwork.V()];
        Queue<Integer> queue = new Queue<>();

        queue.enqueue(s);
        marked[s] = true;

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (FlowEdge edge : flowNetwork.adj(v)) {
                int w = edge.other(v);
                if (!marked[w] && edge.residualCapacityTo(w) > 0) {
                    marked[w] = true;
                    edgeTo[w] = edge;
                    queue.enqueue(w);
                }
            }
        }

        return marked[t];
    }

    public double value() {
        return maxFlow;
    }

    public static void main(String[] args) {

        In in = new In(Inputs.SMALL_FLOW_NETWORK);
        FlowNetwork flowNetwork = new FlowNetwork(in);
        int s = 0;
        int t = flowNetwork.V() - 1;
        FordFulkersonMaxFlow maxFlow = new FordFulkersonMaxFlow(flowNetwork, s, t);
        System.out.printf("Max flow from %d to %d\n", s, t);
        for (int v = 0; v < flowNetwork.V(); v++) {
            for (FlowEdge edge : flowNetwork.adj(v)) {
                if (v == edge.from() && edge.flow() > 0) {
                    System.out.printf(" %s\n", edge);
                }
            }
        }
        System.out.printf("Max flow value = %f\n", maxFlow.value());
    }
}
