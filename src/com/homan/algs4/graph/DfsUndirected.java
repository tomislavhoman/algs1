package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DfsUndirected {

    private static final int NULL = -1;

    private boolean[] visited;
    private int[] distTo;
    private int[] pathTo;

    public DfsUndirected(Graph graph, int s) {
        visited = new boolean[graph.V()];
        distTo = new int[graph.V()];
        pathTo = new int[graph.V()];
        visitGraph(graph, s);
    }

    private void visitGraph(Graph graph, int s) {
        for (int v = 0; v < graph.V(); v++) {
            pathTo[v] = NULL;
            distTo[v] = NULL;
        }
        dfs(s, graph, 0);
    }

    private void dfs(int v, Graph graph, int distance) {
        visited[v] = true;
        distTo[v] = distance;
        for (int neighbour : graph.adj(v)) {
            if (!visited[neighbour]) {
                pathTo[neighbour] = v;
                dfs(neighbour, graph, distance + 1);
            }
        }
    }

    public Iterable<Integer> pathTo(int v) {

        Stack<Integer> path = new Stack<>();
        int w = v;
        while (w != NULL) {
            path.push(w);
            w = pathTo[w];
        }
        return path;
    }

    // How many edges is it far away
    public int distTo(int v) {
        return distTo[v];
    }

    public boolean isReachable(int v) {
        return distTo(v) != NULL;
    }

    public static void main(String[] args) {

        Graph graph = new Graph(new In(Inputs.UNDIRECTED_SMALL));
        DfsUndirected dfs = new DfsUndirected(graph, 0);
        for (int v = 0; v < graph.V(); v++) {
            if (dfs.isReachable(v)) {
                StdOut.printf("Dist to %d: %d.| ", v, dfs.distTo(v));
                for (int w : dfs.pathTo(v)) {
                    StdOut.printf("%d -> ", w);
                }
                StdOut.println();
            } else {
                StdOut.printf("%s is unreachable\n", v);
            }
        }
    }
}
