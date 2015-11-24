package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Stack;

public class TopologicalOrder {

    private boolean visited[];
    private Stack<Integer> topological;

    public TopologicalOrder(Digraph digraph) {

        visited = new boolean[digraph.V()];
        topological = new Stack<>();

        for (int i = 0; i < digraph.V(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(digraph, i);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        for (int w : digraph.adj(v)) {
            if (!visited[w]) {
                visited[w] = true;
                dfs(digraph, w);
            } /*else {
                throw new IllegalStateException("Graph is not a DAG");
            }*/
        }
        topological.push(v);
    }

    public Iterable<Integer> topological() {
        return topological;
    }
}
