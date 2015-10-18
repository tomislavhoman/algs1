import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        for (int i = 0; i < pointsCopy.length; i++) {
            for (int j = i + 1; j < pointsCopy.length; j++) {
                for (int k = j + 1; k < pointsCopy.length; k++) {
                    final double iToj = pointsCopy[i].slopeTo(pointsCopy[j]);
                    final double iTok = pointsCopy[i].slopeTo(pointsCopy[k]);
                    if (iToj != iTok) {
                        continue;
                    }

                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        final double iTol = pointsCopy[i].slopeTo(pointsCopy[l]);
                        final boolean areEqual = iToj == iTok && iToj == iTol;
                        Point[] ordered = new Point[]{pointsCopy[i], pointsCopy[j], pointsCopy[k], pointsCopy[l]};
                        Arrays.sort(ordered);
                        if (areEqual) {
                            segments.add(new LineSegment(ordered[0], ordered[3]));
                        }
                    }
                }
            }
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
}