import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {

    private Node root = null;

    private Point2D currentNearestPoint;
    private double currentMinDistance;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(final Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }

        if (contains(point)) {
            return;
        }

        root = insert(root, point, new RectHV(0.0, 0.0, 1.0, 1.0), 0);
    }

    private Node insert(final Node node, final Point2D point, final RectHV rect, final int depth) {
        if (node == null) {
            return new Node(point, rect, null, null);
        }

        final boolean vertical = depth % 2 == 0;
        int cmp;
        if (vertical) {
            cmp = Double.compare(point.x(), node.point.x());
        } else {
            cmp = Double.compare(point.y(), node.point.y());
        }

        if (cmp >= 0) {
            node.right = insert(node.right, point, buildRightRect(rect, node.point, vertical), depth + 1);
        } else {
            node.left = insert(node.left, point, buildLeftRect(rect, node.point, vertical), depth + 1);
        }

        return node;
    }

    private RectHV buildLeftRect(final RectHV rect, final Point2D point, final boolean vertical) {
        double xmin = rect.xmin();
        double xmax;
        if (vertical) {
            xmax = point.x();
        } else {
            xmax = rect.xmax();
        }

        double ymin = rect.ymin();
        double ymax;
        if (!vertical) {
            ymax = point.y();
        } else {
            ymax = rect.ymax();
        }

        return new RectHV(xmin, ymin, xmax, ymax);
    }

    private RectHV buildRightRect(final RectHV rect, final Point2D point, final boolean vertical) {
        double xmin;
        if (vertical) {
            xmin = point.x();
        } else {
            xmin = rect.xmin();
        }
        double xmax = rect.xmax();

        double ymin;
        if (!vertical) {
            ymin = point.y();
        } else {
            ymin = rect.ymin();
        }
        double ymax = rect.ymax();

        return new RectHV(xmin, ymin, xmax, ymax);
    }

    // does the set contain point p?
    public boolean contains(final Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
        return get(root, point, 0) != null;
    }

    private Node get(final Node node, final Point2D point, final int depth) {
        if (node == null) {
            return null;
        }

        if (node.point.equals(point)) {
            return node;
        }

        int cmp;
        if (depth % 2 == 0) {
            cmp = Double.compare(point.x(), node.point.x());
        } else {
            cmp = Double.compare(point.y(), node.point.y());
        }

        if (cmp >= 0) {
            return get(node.right, point, depth + 1);
        } else {
            return get(node.left, point, depth + 1);
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0);
    }

    private void draw(final Node node, final int depth) {
        if (node == null) {
            return;
        }

        final boolean vertical = depth % 2 == 0;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(node.point.x(), node.point.y());
        StdDraw.setPenRadius();
        Color penColor;
        if (vertical) {
            penColor = StdDraw.RED;
        } else {
            penColor = StdDraw.BLUE;
        }
        StdDraw.setPenColor(penColor);

        if (vertical) {
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        draw(node.left, depth + 1);
        draw(node.right, depth + 1);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        Queue<Point2D> pointsInRange = new Queue<Point2D>();
        range(rect, root, pointsInRange);
        return pointsInRange;
    }

    private void range(final RectHV rect, final Node node, final Queue<Point2D> pointsInRange) {
        if (node == null || !node.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(node.point)) {
            pointsInRange.enqueue(node.point);
        }

        range(rect, node.left, pointsInRange);
        range(rect, node.right, pointsInRange);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(final Point2D point) {
        if (point == null) {
            throw new NullPointerException();
        }
        currentNearestPoint = root.point;
        currentMinDistance = point.distanceTo(root.point);
        nearest(point, root, 0);
        return currentNearestPoint;
    }

    private void nearest(final Point2D queryPoint, final Node node, final int depth) {
        if (node == null) {
            return;
        }

        if (node.rect.distanceTo(queryPoint) > currentMinDistance) {
            return;
        }

        final double queryPointToNode = node.point.distanceTo(queryPoint);
        if (Double.compare(queryPointToNode, currentMinDistance) < 0) {
            currentNearestPoint = node.point;
            currentMinDistance = queryPointToNode;
        }

        final boolean vertical = depth % 2 == 0;
        int cmp;
        if (vertical) {
            cmp = Double.compare(queryPoint.x(), node.point.x());
        } else {
            cmp = Double.compare(queryPoint.y(), node.point.y());
        }

        Node firstToSearch;
        Node secondToSearch;
        if (cmp < 0) {
            firstToSearch = node.left;
            secondToSearch = node.right;
        } else {
            firstToSearch = node.right;
            secondToSearch = node.left;
        }

        nearest(queryPoint, firstToSearch, depth + 1);
        nearest(queryPoint, secondToSearch, depth + 1);
    }

    private static final class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

        private Node(Point2D point, RectHV rect, Node left, Node right) {
            this.point = point;
            this.rect = rect;
            this.left = left;
            this.right = right;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        tree.draw();
    }
}
