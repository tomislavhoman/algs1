import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable = false;
    private int numberOfMoves = 0;
    private Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(final Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        final MinPQ<Node> queue = new MinPQ<>();
        final MinPQ<Node> twinQueue = new MinPQ<>();

        queue.insert(new Node(initial, 0, null));
        twinQueue.insert(new Node(initial.twin(), 0, null));

        while (true) {
            Node current = queue.delMin();
            if (current.board.isGoal()) {
                solution = buildSolution(initial, current);
                solvable = true;
                numberOfMoves = current.numberOfMoves;
                return;
            }
            enqueueNeighbours(current, queue);

            current = twinQueue.delMin();
            if (current.board.isGoal()) {
                solvable = false;
                return;
            }
            enqueueNeighbours(current, twinQueue);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }

        return numberOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private void enqueueNeighbours(final Node current, final MinPQ<Node> queue) {
        for (Board neighbour : current.board.neighbors()) {
            if (current.prev != null && neighbour.equals(current.prev.board)) {
                continue;
            }

            queue.insert(new Node(neighbour, current.numberOfMoves + 1, current));
        }
    }

    private Stack<Board> buildSolution(final Board initial, final Node node) {
        final Stack<Board> result = new Stack<>();
        Node current = node;
        while (current.prev != null) {
            result.push(current.board);
            current = current.prev;
        }
        result.push(initial);
        return result;
    }

    private static final class Node implements Comparable<Node> {
        private final Board board;
        private final int numberOfMoves;
        private final Node prev;

        private Node(Board board, int numberOfMoves, Node prev) {
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.prev = prev;
        }

        private int distance() {
            return board.manhattan() + numberOfMoves;
        }

        @Override
        public int compareTo(Node that) {
            return distance() - that.distance();
        }


    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}