package com.homan.algs4.graph;

// Kosaraju Sharir
public class StronglyConnectedComponents {

    private boolean visited[];
    private int component[];
    private int c;

    public StronglyConnectedComponents(Digraph digraph) {

        visited = new boolean[digraph.V()];
        component = new int[digraph.V()];
        c = -1;

        for (int v : new TopologicalOrder(digraph.reverse()).topological()) {
            if (!visited[v]) {
                visited[v] = true;
                c++;
                component[v] = c;
                dfs(digraph, v);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        for (int w : digraph.adj(v)) {
            if (!visited[w]) {
                visited[w] = true;
                component[w] = c;
                dfs(digraph, w);
            }
        }
    }

    public int component(int v) {
        return component[v];
    }
}
