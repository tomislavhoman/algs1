package com.homan.algs4.graph;

import com.sun.tools.javac.util.Pair;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class DijkstraJavaOnly {

    private static final int NULL = -1;
    private static final double INF = Double.MAX_VALUE;

    private double distTo[];
    private int edgeTo[];

    public DijkstraJavaOnly(String fileName, int s) {

        List<Pair<Integer, Double>>[] graph = buildGraph(fileName);
        if (graph == null) {
            throw new IllegalArgumentException("Wrong input file");
        }

        boolean[] done = initDoneArray(graph.length);
        distTo = initDistToArray(s, graph.length);
        edgeTo = initEdgeToArray(graph.length);

        int v = s;
        while (v != NULL) {

            v = findMinVertex(done);
            if (v == NULL) {
                continue;
            }

            done[v] = true;

            for (Pair<Integer, Double> edge : graph[v]) {
                int w = edge.fst;
                double edgeWeight = edge.snd;

                if (distTo[v] + edgeWeight < distTo[w]) {
                    distTo[w] = distTo[v] + edgeWeight;
                    edgeTo[w] = v;
                }
            }
        }
    }

    private List<Pair<Integer, Double>>[] buildGraph(String fileName) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedInputStream(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            return null;
        }

        int v = scanner.nextInt();
        int e = scanner.nextInt();

        List<Pair<Integer, Double>>[] graph = new List[v];
        for (int i = 0; i < v; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < e; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            double weight = scanner.nextDouble();
            graph[from].add(new Pair<>(to, weight));
        }

        return graph;
    }

    private int[] initEdgeToArray(int length) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = NULL;
        }
        return array;
    }

    private double[] initDistToArray(int s, int length) {
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = INF;
        }
        array[s] = 0.0;
        return array;
    }

    private boolean[] initDoneArray(int length) {
        return new boolean[length];
    }

    private int findMinVertex(boolean[] done) {

        double minValue = INF;
        int minVertex = NULL;
        for (int i = 0; i < distTo.length; i++) {
            if (distTo[i] < minValue && !done[i]) {
                minValue = distTo[i];
                minVertex = i;
            }
        }

        return minVertex;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.MAX_VALUE;
    }

    public Stack<Integer> pathTo(int v) {

        Stack<Integer> path = new Stack<>();

        int w = v;
        while (w != -1) {
            path.push(w);
            w = edgeTo[w];
        }

        return path;
    }
}
