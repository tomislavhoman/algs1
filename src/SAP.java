import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

// Only 90.42%
public class SAP {

    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(final Digraph G) {
        throwIfNull(G);
        this.graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(final int v, final int w) {
        checkBounds(v, w);
        final SET<Integer> setV = new SET<>();
        final SET<Integer> setW = new SET<>();
        setV.add(v);
        setW.add(w);
        return length(setV, setW);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(final int v, final int w) {
        checkBounds(v, w);
        final SET<Integer> setV = new SET<>();
        final SET<Integer> setW = new SET<>();
        setV.add(v);
        setW.add(w);
        return ancestor(setV, setW);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        final Node ancestor = ancestorNode(v, w);
        if (ancestor == null) {
            return -1;
        } else {
            return ancestor.distance;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        final Node ancestor = ancestorNode(v, w);
        if (ancestor == null) {
            return -1;
        } else {
            return ancestor.v;
        }
    }

    private Node ancestorNode(Iterable<Integer> v, Iterable<Integer> w) {
        throwIfNull(v);
        throwIfNull(w);

        final Queue<Node> queueV = new Queue<>();
        final Queue<Node> queueW = new Queue<>();
        enqueueAll(v, queueV);
        enqueueAll(w, queueW);

        int[] visitedV = new int[graph.V()];
        int[] visitedW = new int[graph.V()];
        Arrays.fill(visitedV, -1);
        Arrays.fill(visitedW, -1);

        int minDistance = Integer.MAX_VALUE;
        Node common = null;
        while (!queueV.isEmpty() || !queueW.isEmpty()) {

            if (!queueV.isEmpty()) {
//                int visitingDistance = queueV.peek().distance;
//                while (!queueV.isEmpty() && queueV.peek().distance == visitingDistance) {
                Node node = queueV.dequeue();
                if (visitedW[node.v] > -1) {
                    int distance = node.distance + visitedW[node.v];
                    if (distance < minDistance) {
                        minDistance = distance;
                        common = node;
                    }
                }
                visitedV[node.v] = node.distance;
                for (int neighbour : graph.adj(node.v)) {
                    if (visitedV[neighbour] == -1) {
                        queueV.enqueue(new Node(neighbour, node.distance + 1));
                    }
                }
            }

            if (!queueW.isEmpty()) {
//                int visitingDistance = queueW.peek().distance;
//                while (!queueW.isEmpty() && queueW.peek().distance == visitingDistance) {
                Node node = queueW.dequeue();
                if (visitedV[node.v] > -1) {
                    int distance = node.distance + visitedV[node.v];
                    if (distance < minDistance) {
                        minDistance = distance;
                        common = node;
                    }
                }
                visitedW[node.v] = node.distance;
                for (int neighbour : graph.adj(node.v)) {
                    if (visitedW[neighbour] == -1) {
                        queueW.enqueue(new Node(neighbour, node.distance + 1));
                    }
                }
            }
        }

        if (common == null) {
            return null;
        }

        return new Node(common.v, minDistance);
    }

    private void enqueueAll(Iterable<Integer> iterable, Queue<Node> queue) {
        for (int i : iterable) {
            queue.enqueue(new Node(i, 0));
        }
    }

    private void throwIfNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    private void checkBounds(int v, int w) {
        checkBound(v);
        checkBound(w);
    }

    private void checkBound(int vertex) {
        if (vertex < 0 || vertex >= graph.V()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node {
        public int v;
        public int distance;

        public Node(int v, int distance) {
            this.v = v;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return String.format("V: %d, dist: %d", v, distance);
        }

    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in2 = new In("wordnet/digraph-wordnet.txt");
//        In in2 = new In("wordnet/digraph4.txt");
        Digraph G = new Digraph(in2);
        SAP sap2 = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap2.length(v, w);
            int ancestor = sap2.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
