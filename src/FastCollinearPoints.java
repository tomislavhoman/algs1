import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segments = new ArrayList<>();
        if (points.length < 4) {
            return;
        }

        for (int i = 0; i < pointsCopy.length; i++) {

            Point origin = pointsCopy[i];
            Point[] ordered = Arrays.copyOf(pointsCopy, pointsCopy.length);
            Arrays.sort(ordered, origin.slopeOrder());

            double lastSlope = origin.slopeTo(ordered[1]);
            int fromIndex = 1;

            for (int j = 2; j < ordered.length; j++) {

                double currentSlope = origin.slopeTo(ordered[j]);
                if (lastSlope != currentSlope) {
                    checkAndAddSegment(fromIndex, j - 1, origin, ordered);
                    lastSlope = origin.slopeTo(ordered[j]);
                    fromIndex = j;
                }
            }
            checkAndAddSegment(fromIndex, ordered.length - 1, origin, ordered);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        final LineSegment[] segmentArray = new LineSegment[segments.size()];
        segments.toArray(segmentArray);
        return segmentArray;
    }

    private void checkAndAddSegment(int fromIndex, int toIndex, Point origin, Point[] ordered) {

        if (toIndex - fromIndex + 1 >= 3 && origin.compareTo(ordered[fromIndex]) < 0) {
            segments.add(new LineSegment(origin, ordered[toIndex]));
        }
    }
}
