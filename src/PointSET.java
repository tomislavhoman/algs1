import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private final SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        this.points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    public void insert(final Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
        points.add(point);
    }


    // does the set contain point p?
    public boolean contains(final Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
        return points.contains(point);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        Queue<Point2D> queue = new Queue<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                queue.enqueue(point);
            }
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(final Point2D thePoint) {
        if (thePoint == null) {
            throw new NullPointerException();
        }

        Point2D nearest = null;
        double minimalDistance = Double.MAX_VALUE;
        for (Point2D aPoint : points) {
            final double distance = thePoint.distanceTo(aPoint);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                nearest = aPoint;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}