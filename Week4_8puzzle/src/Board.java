import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private final int[] puzzle;
  private final int D;
  private int hamming;
  private int manhattan;

  public Board(int[][] blocks) {
    D = blocks.length;
    puzzle = new int[D * D];

    hamming = 0;
    manhattan = 0;

    int goalRow = 0;
    int goalCol = 0;
    int n = 0;
    int index = 0;

    for (int i = 0; i < D; i++) {
      for (int j = 0; j < D; j++) {
        puzzle[index++] = blocks[i][j];
      }
    }

    for (int i = 0; i < puzzle.length; i++) {
      if (puzzle[i] != 0 && puzzle[i] !=  (i + 1)) {
        hamming += 1;
      }
    }

    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[0].length; j++) {
        if (blocks[i][j] != 0 && blocks[i][j] != i * D + j + 1) {
          goalCol = (blocks[i][j] - 1) % D;
          goalRow = (blocks[i][j] - 1) / D;
          manhattan += Math.abs(i - goalRow) + Math.abs(j - goalCol);
        }
      }
    }
  }

  public int dimension() {
    return D;
  }

  public int hamming() {
    return hamming;
  }

  public int manhattan() {
    return manhattan;
  }

  public boolean isGoal() {
    boolean isGoal = true;
    for (int i = 0; i < puzzle.length - 1; i++) {
      if (puzzle[i] != i + 1) {
        isGoal = false;
      }
    }
    return isGoal;
  }

  public Board twin() {
    int[] cp = new int[puzzle.length];
    for (int i = 0; i < puzzle.length; i++) {
      cp[i] = puzzle[i];
    }
    for (int i = 1; i < cp.length; i++) {
      if (cp[i - 1] != 0 && cp[i] != 0) {
        exch(cp, i - 1, i);
        int [][] cpCp = expand(cp);
        return new Board(cpCp);
      }
    }
    int [][] cpCp = expand(cp);
    return new Board(cpCp);
  }


  private void exch(int[] block, int i, int j) {
    int temp = block[i];
    block[i] = block[j];
    block[j] = temp;
  }

  private void exch(int[][] block, int i1, int j1, int i2, int j2) {
    int temp = block[i1][j1];
    block[i1][j1] = block[i2][j2];
    block[i2][j2] = temp;
  }

  private int[][] expand(int[] block) {
    int[][] cp = new int[D][D];
    for (int i = 0; i < block.length; i++) {
      cp[i / D][i % D] = block[i];
    }
    return cp;
  }

  @Override
  public boolean equals(Object y) {
    if (y == null || y.getClass() != this.getClass()) {
      return false;
    }
    Board cp = (Board) y;
    if (this.D != cp.dimension()) {
      return false;
    }
    for (int i = 0; i < D * D; i++) {
      if (this.puzzle[i] != cp.puzzle[i]) {
        return false;
      }
    }
    return true;
  }


  public Iterable<Board> neighbors() {
    Stack<Board> neighbors = new Stack<>();
    int rowOfZero = 0;
    int colOfZero = 0;
    int[][] blocks = expand(puzzle);

    FIND:
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[0].length; j++) {
        if (blocks[i][j] == 0) {
          rowOfZero = i;
          colOfZero = j;
          break FIND;
        }
      }
    }

    if (rowOfZero != 0) {

      exch(blocks, rowOfZero, colOfZero, rowOfZero - 1, colOfZero);
      neighbors.push(new Board(blocks));
      exch(blocks, rowOfZero, colOfZero, rowOfZero - 1, colOfZero);
    }

    if (rowOfZero != (D - 1)) {
      exch(blocks, rowOfZero, colOfZero, rowOfZero + 1, colOfZero);
      neighbors.push(new Board(blocks));
      exch(blocks, rowOfZero, colOfZero, rowOfZero + 1, colOfZero);
    }

    if (colOfZero != 0) {
      exch(blocks, rowOfZero, colOfZero, rowOfZero, colOfZero - 1);
      neighbors.push(new Board(blocks));
      exch(blocks, rowOfZero, colOfZero, rowOfZero, colOfZero - 1);
    }

    if (colOfZero != (D - 1)) {
      exch(blocks, rowOfZero, colOfZero, rowOfZero, colOfZero + 1);
      neighbors.push(new Board(blocks));
      exch(blocks, rowOfZero, colOfZero, rowOfZero, colOfZero + 1);
    }

    return neighbors;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(D + "\n");
    for (int i = 0; i < puzzle.length; i++) {
      s.append(" " + puzzle[i] + " ");
      if ((i + 1) % D == 0) {
        s.append("\n");
      }
    }
    return s.toString();
  }

  public static void main(String[] args) {
    int[][] puzzle = {{5, 15, 11, 8}, {0, 9, 12, 1}, {4, 7, 13, 14}, {3, 6, 10, 2}};
    Board board = new Board(puzzle);
//    StdOut.println(board.dimension());
//    StdOut.println(board.hamming());
    StdOut.println(board.manhattan());
//    StdOut.println(board.isGoal());
//    StdOut.println(board);
//    StdOut.println(board.twin());
//    StdOut.println(board);
//    Board board1 = new Board(puzzle);
//    Board board2 = new Board(puzzle);
//    StdOut.println(board1);
//    boolean n = board.equals(board2);
//    StdOut.println(n);
  }
}
