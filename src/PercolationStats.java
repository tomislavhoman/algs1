import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        double[] results = new double[T];
        for (int i = 0; i < T; i++) {
            results[i] = calculateThreshold(N);
        }

        this.mean = StdStats.mean(results);
        this.stdDev = StdStats.stddev(results);

        final double delta = (1.96 * stdDev) / Math.sqrt(T);
        this.confidenceLo = mean - delta;
        this.confidenceHi = mean + delta;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    private double calculateThreshold(final int N) {
        Percolation percolation = new Percolation(N);
        int opened = 0;
        while (!percolation.percolates()) {
            int x = StdRandom.uniform(1, N + 1);
            int y = StdRandom.uniform(1, N + 1);

            if (!percolation.isOpen(x, y)) {
                percolation.open(x, y);
                opened++;
            }
        }

        return ((double) opened / (double) (N * N));
    }

    // test client (described below)
    public static void main(String[] args) {
    }
}