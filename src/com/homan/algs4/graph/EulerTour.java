package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class EulerTour {

    private ST<String, Boolean> visited;
    private boolean hasPath = false;
    private Queue<Integer> path;

    public EulerTour(Graph graph) {
        visited = new ST<>();
        path = new Queue<>();
        dfs(graph, 0);
        hasPath = visited.size() == graph.E() * 2;
    }

    private void dfs(Graph graph, int v) {

        int current = v;
        while (true) {

            path.enqueue(current);
            boolean foundEdge = false;
            for (int w : graph.adj(current)) {
                if (!visited(w, current)) {
                    visited.put(edge(current, w), true);
                    visited.put(edge(w, current), true);
                    current = w;
                    foundEdge = true;
                    break;
                }
            }

            if (!foundEdge) {
                break;
            }
        }
    }

    private boolean visited(int v, int w) {
        return visited.contains(edge(v, w)) || visited.contains(edge(w, v));
    }

    private String edge(int v, int w) {
        return String.format("%d-%d", v, w);
    }

    private Iterable<Integer> path() {
        return path;
    }

    private boolean hasPath() {
        return hasPath;
    }

    public static void main(String[] args) {
        Graph nonEulerGraph = new Graph(new In(Inputs.UNDIRECTED_SMALL));
        Graph eulerGraph = new Graph(new In(Inputs.EULER));
        EulerTour notEuler = new EulerTour(nonEulerGraph);
        EulerTour euler = new EulerTour(eulerGraph);

        if (notEuler.hasPath()) {
            for (int v : notEuler.path()) {
                StdOut.printf("%s -> ", v);
            }
            StdOut.println();
        } else {
            StdOut.print("Graph 1 doesn't have Euler path.\n");
        }

        if (euler.hasPath()) {
            for (int v : euler.path()) {
                StdOut.printf("%s -> ", v);
            }
            StdOut.println();
        } else {
            StdOut.print("Graph 2 doesn't have Euler path.\n");
        }
    }
}
