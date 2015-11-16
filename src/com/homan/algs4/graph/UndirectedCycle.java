package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class UndirectedCycle {

    private boolean[] visited;
    private int[] edgeTo;
    private int cycleV = -1;
    private int cycleW = -1;
    private int s;

    public UndirectedCycle(Graph graph, int s) {
        this.s = s;
        this.visited = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
        dfs(graph, s);
    }

    private void dfs(Graph graph, int v) {

        if (hasCycle()) {
            return;
        }

        visited[v] = true;
        for (int w : graph.adj(v)) {
            if (hasCycle()) {
                return;
            }

            if (!visited[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            } else if (w != edgeTo[v]) {
                cycleV = v;
                cycleW = w;
                return;
            }
        }
    }

    public boolean hasCycle() {
        return cycleW != -1;
    }

    public Iterable<Integer> cycle() {

        Stack<Integer> cycle = new Stack<>();
        int v = cycleV;
        cycle.push(cycleW);
        while (v != 0) {
            cycle.push(v);
            v = edgeTo[v];
        }
        cycle.push(s);
        return cycle;
    }

    public static void main(String[] args) {

        Graph graph = new Graph(new In(Inputs.UNDIRECTED_SMALL));
        UndirectedCycle cycle = new UndirectedCycle(graph, 0);
        if (cycle.hasCycle()) {
            for (int v : cycle.cycle()) {
                StdOut.printf("%d -> ", v);
            }
            StdOut.println();
        } else {
            StdOut.printf("Graph has no cycle.\n");
        }
    }
}
