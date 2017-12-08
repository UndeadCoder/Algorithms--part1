package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double[] openSites;
  private final double fraConfidence;
  private final double meanX;
  private final double stdDev;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("n and trails need to more than 0;");
    }
    openSites = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation per = new Percolation(n);
      while (!per.percolates()) {
        int row = StdRandom.uniform(n) + 1;
        // StdOut.println(row);
        int col = StdRandom.uniform(n) + 1;
        // StdOut.println(row);
        per.open(row, col);
      }
      openSites[i] = per.numberOfOpenSites() / (double)n / (double)n;
    }
    meanX = StdStats.mean(openSites);
    stdDev = StdStats.stddev(openSites);
    fraConfidence = (1.96 * stdDev) / Math.sqrt(openSites.length);
  }


  // sample mean of percolation threshold
  public double mean() {
    return meanX;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return stdDev;
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return meanX - fraConfidence;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return meanX + fraConfidence;
  }

  // test client (described below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trails = Integer.parseInt(args[1]);

    PercolationStats stats = new PercolationStats(n, trails);
    StdOut.println("mean                    = " + stats.mean());
    StdOut.println("stddev                  = " + stats.stddev());
    StdOut.println("95% confidence interval = " + stats.confidenceLo()
        + ", " + stats.confidenceHi());
  }
}


