package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class ConnectedComponents {

    private int numberOfComponents = 0;
    private boolean[] visited;
    private int[] component;

    public ConnectedComponents(Graph graph) {
        visited = new boolean[graph.V()];
        component = new int[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (!visited[v]) {
                dfs(graph, v);
                numberOfComponents++;
            }
        }
    }

    private void dfs(Graph graph, int v) {
        visited[v] = true;
        component[v] = numberOfComponents;
        for (int neighbour : graph.adj(v)) {
            if (!visited[neighbour]) {
                dfs(graph, neighbour);
            }
        }
    }

    public int numberOfComponents() {
        return numberOfComponents;
    }

    public int componentForV(int v) {
        return component[v];
    }

    public static void main(String[] args) {

        Graph graph = new Graph(new In(Inputs.UNDIRECTED_SMALL));
        ConnectedComponents cc = new ConnectedComponents(graph);

        StdOut.printf("Number of components: %d\n", cc.numberOfComponents());
        for (int v = 0; v < graph.V(); v++) {
            StdOut.printf("%d is in component %d\n", v, cc.componentForV(v));
        }
    }
}
