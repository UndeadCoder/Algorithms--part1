import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class BruteCollinearPoints {
  private HashMap<Point, Point> collinearPoints = new HashMap<>();
  private  int numberOfSegments;

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
      for (int j = i + 1; j < points.length; j++) {
        if (points[j] == points[i]) {
          throw new IllegalArgumentException();
        }
      }
    }
    numberOfSegments = 0;
    Point lowest;
    Point highest;
    for (Point p1 : points) {
      lowest = p1;
      for (Point p2 : points) {
        if (!higher(lowest, p2) && p2.compareTo(p1) != 0) {
          highest = p2;
          for (Point p3 : points) {
            if (!higher(lowest, p3) && p3.compareTo(p2) != 0) {
              if (Math.abs(lowest.slopeTo(p3)) == Math.abs(highest.slopeTo(p3))) {
                if (higher(p3, highest)) {
                  highest = p3;
                }
                for (Point p4 : points) {
                  if (!higher(lowest, p4) && p4.compareTo(p3) != 0 && p4.compareTo(p2) != 0) {
                    if (Math.abs(lowest.slopeTo(p4)) == Math.abs(highest.slopeTo(p4))) {
                      if (higher(p4, highest)) {
                        highest = p4;
                      }
                      if (!collinearPoints.containsKey(lowest)
                          || collinearPoints.get(lowest) != highest) {
                        collinearPoints.put(lowest, highest);
                        numberOfSegments++;
                      }
                    }
                  }
                }
              }
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
    return numberOfSegments;
  }

  public LineSegment[] segments() {
    LineSegment[] segments;
    segments = new LineSegment[numberOfSegments];
    int i = 0;
    for (Point p : collinearPoints.keySet()) {
      segments[i++] = new LineSegment(p, collinearPoints.get(p));
    }
    return segments;
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
