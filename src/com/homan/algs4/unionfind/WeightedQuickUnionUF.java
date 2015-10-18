package com.homan.algs4.unionfind;

public final class WeightedQuickUnionUF implements UF {

    private final int[] ids;
    private final int[] sz;

    public WeightedQuickUnionUF(int N) {
        this.ids = new int[N];
        this.sz = new int[N];

        for (int i = 0; i < N; i++) {
            ids[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public void union(final int p, final int q) {

        final int rootP = root(p);
        final int rootQ = root(q);

        if (rootP == rootQ) {
            return;
        }

        if (sz[rootP] < sz[rootQ]) {
            ids[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        } else {
            ids[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
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

    private int root(final int i) {

        int root = i;
        while (root != ids[root]) {
            root = ids[root];
        }
        return root;
    }
}
