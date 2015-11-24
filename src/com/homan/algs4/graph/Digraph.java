package com.homan.algs4.graph;

import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.Set;

public final class Digraph {

    private final int v;
    private final Set<Integer>[] edges;

    private int e;

    @SuppressWarnings("unchecked")
    public Digraph(int v) {
        this.v = v;
        this.edges = (Set<Integer>[]) new Set[v];
        this.e = 0;

        initDataStructures();
    }

    public Digraph(In in) {
        this(in.readInt());
        final int e = in.readInt();
        for (int i = 0; i < e; i++) {
            add(in.readInt(), in.readInt());
        }
    }

    private void initDataStructures() {
        for (int i = 0; i < v; i++) {
            edges[i] = new HashSet<>();
        }
    }

    public int V() {
        return v;
    }

    public int E() {
        return e;
    }

    public Iterable<Integer> adj(final int v) {
        return edges[v];
    }

    public void add(final int v, final int w) {
        // No multiple edges
        if (!edges[v].contains(w)) {
            edges[v].add(w);
            e++;
        }
    }

    public Digraph reverse() {

        final Digraph reversed = new Digraph(v);
        for (int v = 0; v < this.v; v++) {
            for (int w : adj(v)) {
                reversed.add(w, v);
            }
        }
        return reversed;
    }
}
