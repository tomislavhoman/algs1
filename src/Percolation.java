import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private boolean[][] opened;
    private WeightedQuickUnionUF gridFull;
    private WeightedQuickUnionUF gridPercolates;
    private int topVirtual;
    private int bottomVirtual;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;

        this.opened = new boolean[N][N];
        for (int i = 0; i < opened.length; i++) {
            for (int j = 0; j < opened.length; j++) {
                this.opened[i][j] = false;
            }
        }
        this.gridFull = new WeightedQuickUnionUF(N * N + 2);
        this.gridPercolates = new WeightedQuickUnionUF(N * N + 2);
        this.topVirtual = N * N;
        this.bottomVirtual = N * N + 1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkBounds(i, j);

        if (isOpen(i, j)) {
            return;
        }

        setOpened(i - 1, j - 1, true);
        openInternal(i - 1, j - 1);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return isOpened(i - 1, j - 1);
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        return gridFull.connected(topVirtual, convertCoordinates(i - 1, j - 1));
    }

    // does the system percolate?
    public boolean percolates() {
        return gridPercolates.connected(topVirtual, bottomVirtual);
    }

    private void checkBounds(int x, int y) {
        checkBounds(x);
        checkBounds(y);
    }

    private void checkBounds(int index) {
        if (index < 1 || index > N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int convertCoordinates(int x, int y) {
        return x * N + y;
    }

    // Zero based functions
    private void setOpened(int x, int y, boolean value) {
        opened[x][y] = value;
    }

    private boolean isOpened(int x, int y) {
        return opened[x][y];
    }

    private void openInternal(int x, int y) {

        final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < deltas.length; i++) {
            connectNeighbour(x, y, x + deltas[i][0], y + deltas[i][1]);
        }
    }

    private void connectNeighbour(int x, int y, int x1, int y1) {
        if (y1 == -1 || y1 == N) {
            return;
        }

        if (x1 == -1) {
            gridFull.union(convertCoordinates(x, y), topVirtual);
            gridPercolates.union(convertCoordinates(x, y), topVirtual);
            return;
        } else if (x1 == N) {
            gridPercolates.union(convertCoordinates(x, y), bottomVirtual);
            return;
        }

        if (isOpened(x1, y1)) {
            gridFull.union(convertCoordinates(x, y), convertCoordinates(x1, y1));
            gridPercolates.union(convertCoordinates(x, y), convertCoordinates(x1, y1));
        }
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation percolation = new Percolation(2);
        System.out.println(percolation.percolates());
        percolation.open(1, 1);
        System.out.println(percolation.percolates());
        percolation.open(2, 2);
        System.out.println(percolation.percolates());
        percolation.open(2, 1);
        System.out.println(percolation.percolates());
    }
}
