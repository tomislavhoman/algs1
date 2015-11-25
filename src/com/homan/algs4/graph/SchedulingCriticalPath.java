package com.homan.algs4.graph;

import com.sun.tools.javac.util.Pair;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class SchedulingCriticalPath {

    private static final double MIN_INF = Double.MIN_VALUE;

    private int jobs;
    private double[] scheduledAt;

    private boolean[] doneTopological;

    public SchedulingCriticalPath(String fileName) {

        List<Pair<Integer, Double>>[] graph = buildGraph(fileName);
        if (graph == null) {
            throw new IllegalArgumentException("Wrong input");
        }

        scheduledAt = initScheduledAt(graph.length);
        doneTopological = new boolean[graph.length];

        Stack<Integer> topologicalOrder = topologicalSort(graph);

        while (!topologicalOrder.isEmpty()) {
            int v = topologicalOrder.pop();

            for (Pair<Integer, Double> edge : graph[v]) {
                int w = edge.fst;
                double weight = edge.snd;
                if (scheduledAt[v] + weight > scheduledAt[w]) {
                    scheduledAt[w] = scheduledAt[v] + weight;
                }
            }
        }
    }

    private Stack<Integer> topologicalSort(List<Pair<Integer, Double>>[] graph) {

        Stack<Integer> topologicalOrder = new Stack<>();
        for (int i = 0; i < graph.length; i++) {
            if (!doneTopological[i]) {
                doneTopological[i] = true;
                dfs(graph, i, topologicalOrder);
            }
        }
        return topologicalOrder;
    }

    private void dfs(List<Pair<Integer, Double>>[] graph, int v, Stack<Integer> topologicalOrder) {

        for (Pair<Integer, Double> edge : graph[v]) {
            if (!doneTopological[edge.fst]) {
                doneTopological[edge.fst] = true;
                dfs(graph, edge.fst, topologicalOrder);
            }
        }

        topologicalOrder.push(v);
    }

    private double[] initScheduledAt(int size) {
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = MIN_INF;
        }
        return result;
    }

    private List<Pair<Integer, Double>>[] buildGraph(String fileName) {

        try {
            Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(fileName)));
            this.jobs = scanner.nextInt();
            List<Pair<Integer, Double>>[] graph = new List[2 * jobs + 2];
            for (int i = 0; i < graph.length; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < jobs; i++) {

                int job = scanner.nextInt();
                double weight = scanner.nextDouble();

                graph[v(job)].add(new Pair<>(v_(job), weight));
                graph[s(graph)].add(new Pair<>(v(job), 0.0));
                graph[v_(job)].add(new Pair<>(t(graph), 0.0));

                int noSuccessors = scanner.nextInt();

                for (int j = 0; j < noSuccessors; j++) {
                    int successor = scanner.nextInt();
                    graph[v_(job)].add(new Pair<>(v(successor), 0.0));
                }
            }

            return graph;

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private int s(List<Pair<Integer, Double>>[] graph) {
        return graph.length - 2;
    }

    private int t(List<Pair<Integer, Double>>[] graph) {
        return graph.length - 1;
    }

    private int v(int v) {
        return v;
    }

    private int v_(int v) {
        return jobs + v;
    }

    public int jobs() {
        return jobs;
    }

    public double scheduledAt(int job) {
        return scheduledAt[v(job)];
    }

    public static void main(String[] args) {

        SchedulingCriticalPath scheduler = new SchedulingCriticalPath(Inputs.SCHEDULING);
        for (int i = 0; i < scheduler.jobs(); i++) {
            System.out.printf("Job %d scheduled at: %f\n", i, scheduler.scheduledAt(i));
        }
        System.out.println();
    }
}
