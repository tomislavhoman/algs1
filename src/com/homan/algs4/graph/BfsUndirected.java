package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BfsUndirected {

    private static final int NULL = -1;

    private boolean[] visited;
    private int[] edgeTo;
    private int[] distTo;

    public BfsUndirected(Graph graph, int s) {

        visited = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        distTo = new int[graph.V()];
        for (int v = 0; v < graph.V(); v++) {
            edgeTo[v] = NULL;
            distTo[v] = NULL;
        }

        bfs(graph, s);
    }

    private void bfs(Graph graph, int s) {

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        visited[s] = true;
        distTo[s] = 0;

        while (!queue.isEmpty()) {
            int v = queue.dequeue();

            for (int neighbour : graph.adj(v)) {
                if (!visited[neighbour]) {
                    edgeTo[neighbour] = v;
                    visited[neighbour] = true;
                    distTo[neighbour] = distTo[v] + 1;
                    queue.enqueue(neighbour);
                }
            }
        }
    }

    private boolean isReachable(int v) {
        return distTo[v] != NULL;
    }

    private int distTo(int v) {
        return distTo[v];
    }

    private Iterable<Integer> pathTo(int v) {

        Stack<Integer> path = new Stack<>();
        int w = v;
        while (w != NULL) {
            path.push(w);
            w = edgeTo[w];
        }
        return path;
    }

    public static void main(String[] args) {

        Graph graph = new Graph(new In(Inputs.UNDIRECTED_SMALL));
        BfsUndirected bfs = new BfsUndirected(graph, 12);
        for (int v = 0; v < graph.V(); v++) {
            if (bfs.isReachable(v)) {
                StdOut.printf("Dist to %d: %d.| ", v, bfs.distTo(v));
                for (int w : bfs.pathTo(v)) {
                    StdOut.printf("%d -> ", w);
                }
                StdOut.println();
            } else {
                StdOut.printf("%s is unreachable\n", v);
            }
        }
    }
}
