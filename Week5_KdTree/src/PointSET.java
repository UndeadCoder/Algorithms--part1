import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointTreeSet;

    public PointSET() {
        pointTreeSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointTreeSet.isEmpty();
    }

    public int size() {
        return pointTreeSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        pointTreeSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return pointTreeSet.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point2D : pointTreeSet) {
            point2D.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> temp = new Stack<>();
        for (Point2D point2D : pointTreeSet) {
            if (rect.contains(point2D)) {
                temp.push(point2D);
            }
        }
        return temp;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearestPoint = pointTreeSet.first();
        double nearestDistance = p.distanceTo(pointTreeSet.first());
        for (Point2D point2D : pointTreeSet) {
            double distance = p.distanceTo(point2D);
            if (distance < nearestDistance) {
                nearestPoint = point2D;
                nearestDistance = distance;
            }
        }
        return nearestPoint;
    }
}
