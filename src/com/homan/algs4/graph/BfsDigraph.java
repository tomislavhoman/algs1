package com.homan.algs4.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.Queue;

public class BfsDigraph {

    private static final int INFINITY = Integer.MAX_VALUE;

    private boolean visited[];
    private int distTo[];
    private int edgeTo[];

    public BfsDigraph(Digraph digraph, int s) {
        visited = new boolean[digraph.V()];
        distTo = new int[digraph.V()];
        edgeTo = new int[digraph.V()];

        for (int i = 0; i < digraph.V(); i++) {
            distTo[i] = INFINITY;
            edgeTo[i] = INFINITY;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        distTo[s] = 0;
        visited[s] = true;

        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int w : digraph.adj(v)) {
                if (!visited[w]) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    visited[w] = true;
                    queue.add(w);
                }
            }
        }
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> stack = new Stack<>();

        int w = v;
        while (w != INFINITY) {
            stack.push(w);
            w = edgeTo[w];
        }

        return stack;
    }

    public boolean isReachable(int v) {
        return distTo[v] != INFINITY;
    }

    public static void main(String[] args) {

        In in = new In(Inputs.DIGRAPH_SMALL);
        Digraph digraph = new Digraph(in);
        BfsDigraph bfs = new BfsDigraph(digraph, 0);

        for (int v = 0; v < digraph.V(); v++) {
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
