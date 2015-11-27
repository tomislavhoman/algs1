import edu.princeton.cs.algs4.AcyclicSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

// Works on my computer, but doesn't find any paths on grader. Therefore 48.17%
public class SeamCarver2 {

    private static final double EDGE_ENERGY = 1000.0;
    private static final short[] DELTAS = {-1, 0, 1};

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver2(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(final int x, final int y) {
        checkBounds(x, y);

        if (onTheEdge(x, y)) {
            return EDGE_ENERGY;
        }

        final Color leftPixel = picture.get(x - 1, y);
        final Color rightPixel = picture.get(x + 1, y);
        final double RX = leftPixel.getRed() - rightPixel.getRed();
        final double GX = leftPixel.getGreen() - rightPixel.getGreen();
        final double BX = leftPixel.getBlue() - rightPixel.getBlue();

        final Color topPixel = picture.get(x, y - 1);
        final Color bottomPixel = picture.get(x, y + 1);
        final double RY = topPixel.getRed() - bottomPixel.getRed();
        final double GY = topPixel.getGreen() - bottomPixel.getGreen();
        final double BY = topPixel.getBlue() - bottomPixel.getBlue();

        final double deltaXSquared = (RX * RX) + (GX * GX) + (BX * BX);
        final double deltaYSquared = (RY * RY) + (GY * GY) + (BY * BY);

        return Math.sqrt(deltaXSquared + deltaYSquared);
    }

    // sequence of indices for vertical seam
//    public int[] findVerticalSeam() {
//        final int width = width();
//        final int height = height();
//        final double[][] energyMatrix = new double[width][height];
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                energyMatrix[i][j] = energy(i, j);
//            }
//        }
//
//        final int[] seam = new int[height];
//        final double[][] distTo = new double[width][height];
//        for (int x = 0; x < width; x++) {
//            distTo[x][0] = EDGE_ENERGY;
//        }
//
//        for (int y = 1; y < height; y++) {
//
//            double minInRow = Double.MAX_VALUE;
//            distTo[0][y] = distTo[0][y - 1] + EDGE_ENERGY;
//            distTo[width - 1][y] = distTo[width - 1][y - 1] + EDGE_ENERGY;
//
//            for (int x = 1; x < width - 1; x++) {
//                final double dist1 = distTo[x - 1][y - 1] + energyMatrix[x][y];
//                final double dist2 = distTo[x][y - 1] + energyMatrix[x][y];
//                final double dist3 = distTo[x + 1][y - 1] + energyMatrix[x][y];
//
//                double min = dist1;
//                int minIndex = x - 1;
//                if (Double.compare(dist2, min) < 0) {
//                    min = dist2;
//                    minIndex = x;
//                }
//
//                if (Double.compare(dist3, min) < 0) {
//                    min = dist3;
//                    minIndex = x + 1;
//                }
//
//                distTo[x][y] = min;
//
//                if (Double.compare(min, minInRow) < 0) {
//                    minInRow = min;
//                    seam[y - 1] = minIndex;
//                }
//            }
//        }
//
//        seam[seam.length - 1] = seam[seam.length - 2];
//
//        return seam;
//    }

//    public int[] findVerticalSeam() {
//        final int width = width();
//        final int height = height();
//        final double[][] energyMatrix = new double[width][height];
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                energyMatrix[x][y] = energy(x, y);
//            }
//        }
//
//        double dist[][] = new double[width][height];
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (x == 0) {
//                    dist[x][y] = energyMatrix[x][y];
//                } else {
//                    dist[x][y] = Double.MAX_VALUE;
//                }
//            }
//        }
//
//        for (int x = 0; x < width - 1; x++) {
//            for (int y = 0; y < height; y++) {
//                for (int delta : deltas) {
//                    if (y + delta < 0 || y + delta > height - 1) {
//                        continue;
//                    }
//
//                    final double newDist = dist[x][y] + energyMatrix[x + 1][y + delta];
//                    if (Double.compare(newDist, dist[x + 1][y + delta]) < 0) {
//                        dist[x + 1][y + delta] = newDist;
//                    }
//                }
//            }
//        }
//
//        final int[] seam = new int[height];
//        seam[seam.length - 1] = seam[seam.length - 2];
//        return seam;
//    }


    // Using course data structures
    public int[] findVerticalSeam() {
        final int width = width();
        final int height = height();

        final EdgeWeightedDigraph graph = new EdgeWeightedDigraph(width * height + 2);
        final int source = width * height;
        final int sink = width * height + 1;
        for (int i = 0; i < width; i++) {
            int v = positionToVertex(i, 0, width);
            graph.addEdge(new DirectedEdge(source, v, EDGE_ENERGY));
            v = positionToVertex(i, height - 1, width);
            graph.addEdge(new DirectedEdge(v, sink, 0));
        }

        for (int j = 0; j < height - 1; j++) {
            for (int i = 0; i < width; i++) {
                for (int delta : DELTAS) {
                    int ii = i + delta;
                    if (ii < 0 || ii > width - 1) {
                        continue;
                    }

                    int v = positionToVertex(i, j, width);
                    int w = positionToVertex(ii, j + 1, width);
                    final double energy = energy(ii, j + 1);
                    graph.addEdge(new DirectedEdge(v, w, energy));
                }
            }
        }

        final AcyclicSP sp = new AcyclicSP(graph, source);
        //final double dist = sp.distTo(sink);

        int[] seem = new int[height];
        int i = 0;
        for (DirectedEdge e : sp.pathTo(sink)) {
            if (i < height) {
                seem[i++] = vertexToX(e.to(), width);
            }
        }

        return seem;
    }

    private int positionToVertex(int i, int j, int width) {
        return j * width + i;
    }

    private int vertexToX(int v, int width) {
        return v % width;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transposePicture();
        final int[] seam = findVerticalSeam();
        transposePicture();
        return seam;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(final int[] seam) {
        checkSeam(seam, height(), width());

        final int width = width() - 1;
        final int height = height();
        final Picture newPicture = new Picture(width, height);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int index;
                if (i < seam[j]) {
                    index = i;
                } else {
                    index = i + 1;
                }
                final Color color = picture.get(index, j);
                newPicture.set(i, j, color);
            }
        }

        picture = newPicture;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(final int[] seam) {
        checkSeam(seam, width(), height());
        transposePicture();
        removeVerticalSeam(seam);
        transposePicture();
    }

    private boolean onTheEdge(int x, int y) {
        return x == 0 || x == width() - 1 || y == 0 || y == height() - 1;
    }

    private void checkBounds(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkSeam(final int[] seam, final int seamLength, final int seamRange) {
        if (seam == null) {
            throw new NullPointerException();
        }

        if (seam.length != seamLength) {
            throw new IllegalArgumentException();
        }

        if (seamRange <= 1) {
            throw new IllegalArgumentException();
        }

        if (seam.length == 0) {
            return;
        }

        int lastPosition = seam[0];
        for (int position : seam) {
            if (position < 0 || position > seamRange) {
                throw new IllegalArgumentException();
            }

            if (Math.abs(position - lastPosition) > 1) {
                throw new IllegalArgumentException();
            }
            lastPosition = position;
        }
    }

    private void transposePicture() {
        final Picture transposedPicture = new Picture(picture.height(), picture.width());
        final int width = transposedPicture.width();
        final int height = transposedPicture.height();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                transposedPicture.set(i, j, picture.get(j, i));
            }
        }

        picture = transposedPicture;
    }
}