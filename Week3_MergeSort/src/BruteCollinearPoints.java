import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private final ArrayList<LineSegment> segmentsList = new ArrayList<>();
  
  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (Point p : points) {
      if (p == null) {
        throw new IllegalArgumentException();
      }
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        if (i != j && points[j].compareTo(points[i]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }
    Point[] pointsCopy = Arrays.copyOf(points, points.length);
    Arrays.sort(pointsCopy);
    Point lowest;
    Point highest;
    Arrays.sort(points);
    for (int p = 0; p < pointsCopy.length - 3; p++) {
      for (int q = p + 1; q < pointsCopy.length - 2; q++) {
        for (int r = q + 1; r < pointsCopy.length - 1; r++) {
          for (int s = r + 1; s < pointsCopy.length; s++) {
            if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r]) &&
              pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
              segmentsList.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
            }
          }
        }
      }
    }
  }

  private boolean higher(Point v, Point w) {
    return v.compareTo(w) >= 0;
  }

  public int numberOfSegments() {
    return segmentsList.size();
  }

  public LineSegment[] segments() {
    LineSegment[] segments = new LineSegment[segmentsList.size()];
    return segmentsList.toArray(segments);
  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }

}
