import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

  private final ArrayList<LineSegment> segmentsList;

  public FastCollinearPoints(Point[] points) {
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
        if (i != j && points[j].compareTo(points[i]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }
    segmentsList = new ArrayList<>();
    Point[] pointsClone = Arrays.copyOf(points, points.length);
    for (int i = 0; i < points.length; i++) {
      int start = 0;
      int end = start;

      Arrays.sort(pointsClone);
      Arrays.sort(pointsClone, points[i].slopeOrder());
      while (start < pointsClone.length) {
        while (end < pointsClone.length && points[i].slopeTo(pointsClone[end])
            == points[i].slopeTo(pointsClone[start])) {
          end++;
        }
        if (end - start >= 3) {
          Point pointMin = points[i].compareTo(pointsClone[start])
              > 0 ? pointsClone[start] : points[i];
          Point pointMax = points[i].compareTo(pointsClone[start])
              > 0 ? points[i] : pointsClone[end - 1];
          if (points[i] == pointMin) {
            segmentsList.add(new LineSegment(pointMin, pointMax));
          }
        }
        start = end;
      }
    }
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
