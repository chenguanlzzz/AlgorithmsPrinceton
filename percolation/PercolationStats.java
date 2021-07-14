import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percoTrials;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        percoTrials = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percoSim = new Percolation(n);
            while (!percoSim.percolates()) {
                int row = StdRandom.uniform(0, n) + 1;
                int col = StdRandom.uniform(0, n) + 1;
                percoSim.open(row, col);
            }
            percoTrials[i] = (double) percoSim.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percoTrials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percoTrials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = ["
                + stats.confidenceLo()
                + ", "
                + stats.confidenceHi()
                + "]");
    }

}
