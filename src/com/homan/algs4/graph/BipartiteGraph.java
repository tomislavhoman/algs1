package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BipartiteGraph {

    private static boolean RED = true;
    private static boolean BLACK = false;

    private boolean[] visited;
    private boolean[] colors;
    private boolean isBipartite;

    public BipartiteGraph(Graph graph) {
        visited = new boolean[graph.V()];
        colors = new boolean[graph.V()];
        dfs(graph, 0, RED);

        isBipartite = true;
        for (int v = 0; v < graph.V() && isBipartite; v++) {
            for (int w : graph.adj(v)) {
                if (colors[w] == colors[v]) {
                    isBipartite = false;
                }
            }
        }
    }

    private void dfs(Graph graph, int v, boolean color) {

        visited[v] = true;
        colors[v] = color;
        for (int w : graph.adj(v)) {
            if (!visited[w]) {
                dfs(graph, w, !color);
            }
        }
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(new In(Inputs.UNDIRECTED_SMALL));
        BipartiteGraph bipartiteGraph = new BipartiteGraph(graph);
        if (bipartiteGraph.isBipartite()) {
            StdOut.print("Graph is bipartite.\n");
        } else {
            StdOut.print("Graph is bipartite.\n");
        }
    }
}
