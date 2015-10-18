package com.homan.algs4.unionfind;

public final class QuickFindUF implements UF {

    private final int[] ids;

    public QuickFindUF(int N) {
        this.ids = new int[N];

        for (int i = 0; i < N; i++) {
            ids[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {

        final int idp = ids[p];
        final int idq = ids[q];

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == idp) {
                ids[i] = idq;
            }
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return ids[p] == ids[q];
    }

    @Override
    public int find(int p) {
        return 0;
    }

    @Override
    public int count() {
        return 0;
    }
}
