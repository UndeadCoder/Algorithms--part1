import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private final int[] puzzle;
  private final int[][] blocks;
  private final int D;
  private int hamming;
  private int manhattan;

  public Board(int[][] blocks) {
    D = blocks.length;
    this.blocks = new int[D][D];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        this.blocks[i][j] = blocks[i][j];
      }
    }
    puzzle = new int[D * D];

    hamming = 0;
    manhattan = 0;

    int current = 0;
    int goal = 0;
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

    for (int i = 0; i < puzzle.length; i++) {
      if (puzzle[i] != 0 && puzzle[i] != (i + 1)) {
        current = i;
        goal = puzzle[i] - 1;
        n = (current - goal) / D;
        manhattan += Math.abs(n + (current - n * D) - goal);
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
    int[][] block = new int[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[0].length; j++) {
        block[i][j] = blocks[i][j];
      }
    }
    for (int i = 0; i < block.length; i++) {
      for (int j = 1; j < block[i].length; i++) {
        if (block[i][j - 1] != 0 && block[i][j] != 0) {
          exch(block, i, j);
          return new Board(block);
        }
      }
    }
    return new Board(block);
  }


  private void exch(int[][] block, int i, int j) {
    int temp = block[i][j];
    block[i][j] = block[i][j - 1];
    block[i][j - 1] = temp;
  }

  private void exch(int[][] blocks, int i1, int j1, int i2, int j2) {
    int temp = blocks[i1][j1];
    blocks[i1][j1] = blocks[i2][j2];
    blocks[i2][j2] = temp;
  }

  @Override
  public boolean equals(Object y) {
    if (y == null || !(y instanceof Board)) {
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

    FIND:
    for (int i = 0; i < this.blocks.length; i++) {
      for (int j = 0; j < this.blocks[0].length; j++) {
        if (this.blocks[i][j] == 0) {
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
    StringBuffer board = new StringBuffer(D + "\n");
    for (int i = 0; i < puzzle.length; i++) {
      board.append(" " + puzzle[i] + " ");
      if ((i + 1) % D == 0) {
        board.append("\n");
      }
    }
    return board.toString();
  }

  public static void main(String[] args) {
    int[][] puzzle = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    Board board = new Board(puzzle);
    StdOut.println(board.dimension());
    StdOut.println(board.hamming());
    StdOut.println(board.manhattan());
    StdOut.println(board.isGoal());
    StdOut.println(board);
    StdOut.println(board.twin());
    StdOut.println(board);
    Board board1 = new Board(puzzle);
    Board board2 = new Board(puzzle);
    StdOut.println(board1);
    boolean n = board.equals(board2);
    StdOut.println(n);
  }
}
