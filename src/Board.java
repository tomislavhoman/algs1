import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    private static final int UNKNOWN = -1;
    private int[][] blocks;
    private int hamming = UNKNOWN;
    private int manhattan = UNKNOWN;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(final int[][] blocksIn) {
        if (blocksIn == null) {
            throw new NullPointerException();
        }

        blocks = copyInputArray(blocksIn);
    }

    // board dimension N
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming == UNKNOWN) {
            int numberOfOutOfPlaceBlocks = 0;
            final int n = dimension();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] == 0) {
                        continue;
                    }

                    if (blockOnPosition(i, j, dimension()) != blocks[i][j]) {
                        numberOfOutOfPlaceBlocks++;
                    }
                }
            }
            hamming = numberOfOutOfPlaceBlocks;
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan == UNKNOWN) {
            int manhattanDistances = 0;
            final int n = dimension();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    final int element = blocks[i][j];
                    if (element == 0) {
                        continue;
                    }

                    final int x = (element - 1) / n;
                    final int y = (element - 1) % n;
                    manhattanDistances += (Math.abs(x - i) + Math.abs(y - j));
                }
            }
            manhattan = manhattanDistances;
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        final int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }

                if (blockOnPosition(i, j, n) != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        final int n = dimension();
        if (n == 1) {
            return new Board(blocks);
        }

        final int[][] copy = copyInputArray(blocks);
        final int x1 = 0;
        int y1 = 0;
        if (copy[x1][y1] == 0) {
            y1++;
        }
        final int x2 = n - 1;
        int y2 = n - 1;
        if (copy[x2][y2] == 0) {
            y2--;
        }

        swap(copy, x1, y1, x2, y2);
        return new Board(copy);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (!(y.getClass() == Board.class)) {
            return false;
        }

        Board that = (Board) y;
        return Arrays.deepEquals(blocks, that.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        final Queue<Board> neighbors = new Queue<>();
        final int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    final short[][] deltas = new short[][]
                            {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                    for (int k = 0; k < 4; k++) {
                        final int ii = i + deltas[k][0];
                        final int jj = j + deltas[k][1];
                        if (ii <= -1 || ii >= n || jj <= -1 || jj >= n) {
                            continue;
                        }

                        final int[][] copy = copyInputArray(blocks);
                        swap(copy, i, j, ii, jj);
                        neighbors.enqueue(new Board(copy));
                    }
                }
            }
        }
        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        final StringBuilder s = new StringBuilder();
        final int N = dimension();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int[][] copyInputArray(final int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            int[] blocksInRow = source[i];
            int length = blocksInRow.length;
            copy[i] = new int[length];
            System.arraycopy(blocksInRow, 0, copy[i], 0, length);
        }
        return copy;
    }

    private int blockOnPosition(int x, int y, int n) {
        return x * n + y + 1;
    }

    private void swap(int[][] array, int x1, int y1, int x2, int y2) {
        final int tmp = array[x1][y1];
        array[x1][y1] = array[x2][y2];
        array[x2][y2] = tmp;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
    }
}