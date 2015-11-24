package com.homan.algs4.graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class KosarajuSharirJavaOnly {

    private boolean visited[];
    private boolean visitedReversed[];
    private int component[];

    public KosarajuSharirJavaOnly(String filename) {

        int v = -1;
        Set<Integer>[] graph = null;

        try {
            boolean parsingV = true;
            boolean parsingE = false;
            boolean parsingEdges = false;

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (parsingV) {
                    v = Integer.parseInt(line);

                    visited = new boolean[v];
                    visitedReversed = new boolean[v];
                    graph = (HashSet<Integer>[]) new HashSet[v];
                    component = new int[v];

                    for (int i = 0; i < v; i++) {
                        graph[i] = new HashSet<>();
                        component[i] = -1;
                    }

                    parsingV = false;
                    parsingE = true;
                    continue;
                }

                if (parsingE) {
                    int e = Integer.parseInt(line); //e not used
                    parsingE = false;
                    parsingEdges = true;
                    continue;
                }

                if (parsingEdges) {
                    String[] edges = line.split(" ");
                    int from = -1;
                    int to = -1;
                    for (int i = 0; i < edges.length; i++) {
                        if ("".equals(edges[i])) {
                            continue;
                        }

                        if (from == -1) {
                            from = Integer.parseInt(edges[i]);
                            continue;
                        }

                        to = Integer.parseInt(edges[i]);
                    }
                    graph[from].add(to);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (v == -1) {
            return;
        }

        Set<Integer>[] reversed = new Set[v];
        for (int i = 0; i < v; i++) {
            reversed[i] = new HashSet<>();
        }

        for (int i = 0; i < v; i++) {
            for (int j : graph[i]) {
                reversed[j].add(i);
            }
        }

        Stack<Integer> postOrder = new Stack<>();
        for (int i = 0; i < v; i++) {
            if (!visitedReversed[i]) {
                visitedReversed[i] = true;
                visitReversed(reversed, i, postOrder);
            }
        }

        int componentId = -1;
        while (!postOrder.isEmpty()) {
            int i = postOrder.pop();
            if (!visited[i]) {
                visited[i] = true;
                componentId++;
                component[i] = componentId;
                visitGraph(graph, i, componentId);
            }
        }
    }

    private void visitGraph(Set<Integer>[] graph, int v, int componentId) {
        for (int w : graph[v]) {
            if (!visited[w]) {
                visited[w] = true;
                component[w] = componentId;
                visitGraph(graph, w, componentId);
            }
        }
    }

    private void visitReversed(Set<Integer>[] reversed, int v, Stack<Integer> stack) {
        for (int w : reversed[v]) {
            if (!visitedReversed[w]) {
                visitedReversed[w] = true;
                visitReversed(reversed, w, stack);
            }
        }
        stack.push(v);
    }

    public int component(int v) {
        return component[v];
    }
}
