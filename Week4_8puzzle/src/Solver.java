import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {

  private MinPQ<Node> solution;
  private MinPQ<Node> twinSolution;
  private Queue<Board> solutions;
  private int move;
  private boolean solvable;

  private class Node implements Comparable<Node> {
    private final Board board;
    private Node predecessor;
    private int moves;
    private final int priority;

    private Node(Board board, Node predecessor) {
      this.board = board;
      this.predecessor = predecessor;
      if (predecessor != null) {
        this.moves = predecessor.moves + 1;
      } else {
        moves = 0;
      }
      this.priority = board.manhattan() + moves;
    }

    @Override
    public int compareTo(Node that) {
      return Integer.compare(this.priority, that.priority);
    }

    public boolean isGoal() {
      return board.isGoal();
    }

  }

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    solutions = new Queue<>();
    solvable = true;
    if (initial.isGoal()) {
      solutions.enqueue(initial);
    } else {
      twinSolution = new MinPQ<>();
      solution = new MinPQ<>();
      solve(initial);
    }
  }

  private void solve(Board initial) {
    if (initial.twin().isGoal()) {
      solvable =false;
      return;
    }
    solutions.enqueue(initial);
    Node initialNode = new Node(initial, null);
    Node twinInitialNode = new Node(initial.twin(), null);
    solution.insert(initialNode);
    twinSolution.insert(twinInitialNode);
    Node predecessor = solution.delMin();
    Node twinPredecessor = twinSolution.delMin();
    for (Board board : predecessor.board.neighbors()) {
      solution.insert(new Node(board, predecessor));
    }
    for (Board board : twinPredecessor.board.neighbors()) {
      if (board.isGoal()) {
        solvable = false;
        return;
      }
      twinSolution.insert(new Node(board, predecessor));
    }
    Node currentNode = solution.delMin();
    Node twinCurrentNode = twinSolution.delMin();
    move = currentNode.moves;
    int twinMove = twinCurrentNode.moves;
    solutions.enqueue(currentNode.board);
    while (!currentNode.isGoal()) {
      for (Board board : twinCurrentNode.board.neighbors()) {
        if (board.isGoal()) {
          solvable = false;
          return;
        }
        if (!board.equals(twinCurrentNode.predecessor.board)) {
          twinSolution.insert(new Node(board, currentNode));
        }
      }
      for (Board board : currentNode.board.neighbors()) {
        if (!board.equals(currentNode.predecessor.board)) {
          solution.insert(new Node(board, currentNode));
        }
      }
      do {
        currentNode = solution.delMin();
      } while (currentNode.moves <= move);
      move = currentNode.moves;
      solutions.enqueue(currentNode.board);
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return solvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return move;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solutions;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
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
