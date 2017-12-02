package percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[] oc;
  private final WeightedQuickUnionUF wquf;
  private final WeightedQuickUnionUF wqufWithoutBottom;

  private final int numOfRow;
  private final int virtualTopSite;
  private final int virtualBottomSite;
  private int numOfOpenSites;

  /**
   * Constructor.
   * @param n The length of grid.
   * @exception Throw IllegalArgumentException if n < 0.
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("N need to more than 0;");
    }
    this.numOfRow = n;
    oc = new boolean[numOfRow * numOfRow];
    wquf = new WeightedQuickUnionUF((numOfRow * numOfRow) + 2);
    wqufWithoutBottom = new WeightedQuickUnionUF(((numOfRow * numOfRow) + 1));
    virtualTopSite = numOfRow * numOfRow;
    virtualBottomSite = numOfRow  * numOfRow  + 1;
  }

  /**
   * Open a dot and then connect it to its neighbour.
   * @param row The x axis of dot.
   * @param col The y axis of dot.
   */
  public void open(int row, int col) {
    int dot = xyTo1D(row, col);
    if (!oc[dot]) {
      oc[dot] = true;
      numOfOpenSites += 1;
      if (row == 1) {
        wquf.union(virtualTopSite, dot);
        wqufWithoutBottom.union(virtualTopSite, dot);
      }
      if (row == numOfRow) {
        wquf.union(virtualBottomSite, dot);
      }
      if (isArgumentLegal(row, (col - 1)) && isOpen(row, (col - 1))) {
        wquf.union(xyTo1D(row, (col - 1)), dot);
        wqufWithoutBottom.union(xyTo1D(row, (col - 1)), dot);
      }
      if (isArgumentLegal((row - 1), col) && isOpen((row - 1), col)) {
        wquf.union(xyTo1D((row - 1), col), dot);
        wqufWithoutBottom.union(xyTo1D((row - 1), col), dot);
      }
      if (isArgumentLegal(row, (col + 1)) && isOpen(row, (col + 1))) {
        wquf.union(xyTo1D(row, (col + 1)), dot);
        wqufWithoutBottom.union(xyTo1D(row, (col + 1)), dot);
      }
      if (isArgumentLegal((row + 1), col) && isOpen((row + 1), col)) {
        wquf.union(xyTo1D((row + 1), col), dot);
        wqufWithoutBottom.union(xyTo1D((row + 1), col), dot);
      }
    }
  }

  private int xyTo1D(int row, int col) {
    if (isArgumentLegal(row, col)) {
      //  StdOut.println(grid[row-1][col-1]);
      return (row - 1) * numOfRow + col - 1;
    } else {
      throw new IllegalArgumentException(
        "The index of row and col should be between 1 and " + numOfRow + ".");
    }
  }

  private boolean isArgumentLegal(int row, int col) {
    return !(row < 1 || row > numOfRow  || col < 1 || col > numOfRow);
  }

  public boolean isOpen(int row, int col) {
    return oc[xyTo1D(row, col)];
  }

  public boolean isFull(int row, int col) {
    return wqufWithoutBottom.connected(virtualTopSite, xyTo1D(row, col));
  }

  /**
   * Return the number of open sites.
   * @return number The total number of open sites.
   */
  public int numberOfOpenSites() {
    // StdOut.println(numOfOpenSites);
    return numOfOpenSites;
  }

  public boolean percolates() {
    return wquf.connected(virtualTopSite, virtualBottomSite);
  }
}