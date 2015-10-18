package com.homan.algs4.unionfind;

public final class QuickUnionUF implements UF {

    private final int[] ids;

    public QuickUnionUF(int N) {
        this.ids = new int[N];

        for (int i = 0; i < N; i++) {
            this.ids[i] = i;
        }
    }

    @Override
    public void union(final int p, final int q) {
        ids[root(p)] = root(q);
    }

    @Override
    public boolean connected(final int p, final int q) {
        return root(p) == root(q);
    }

    @Override
    public int find(final int p) {
        return 0;
    }

    @Override
    public int count() {
        return 0;
    }

    private int root(final int id) {

        int root = id;
        while (ids[root] != root) {
            root = ids[root];
        }

        return root;
    }
}
