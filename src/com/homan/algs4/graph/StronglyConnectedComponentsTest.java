package com.homan.algs4.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KosarajuSharirSCC;
import edu.princeton.cs.algs4.StdOut;

public class StronglyConnectedComponentsTest {

    public static void main(String[] args) {

        In in1 = new In(Inputs.DIGRAPH_SMALL);
        In in2 = new In(Inputs.DIGRAPH_SMALL);
        Digraph digraph1 = new Digraph(in1);
        edu.princeton.cs.algs4.Digraph digraph2 = new edu.princeton.cs.algs4.Digraph(in2);

        StronglyConnectedComponents scc1 = new StronglyConnectedComponents(digraph1); //My
        KosarajuSharirSCC scc2 = new KosarajuSharirSCC(digraph2); // Sedgewick

        // My using codility approach - Java claases only
        KosarajuSharirJavaOnly scc3 = new KosarajuSharirJavaOnly(Inputs.DIGRAPH_SMALL);

        StdOut.printf("My implementation\n");
        for (int v = 0; v < digraph1.V(); v++) {
            StdOut.printf("%d in %d\n", v, scc1.component(v));
        }
        StdOut.println();

        StdOut.printf("Sedgewick implementation\n");
        for (int v = 0; v < digraph2.V(); v++) {
            StdOut.printf("%d in %d\n", v, scc2.id(v));
        }
        StdOut.println();

        StdOut.printf("My Java only implementation\n");
        for (int v = 0; v < digraph2.V(); v++) {
            StdOut.printf("%d in %d\n", v, scc3.component(v));
        }
        StdOut.println();
    }
}
