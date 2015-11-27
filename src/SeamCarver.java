import edu.princeton.cs.algs4.Picture;

import java.awt.*;

// 100%
public class SeamCarver {

    private static final double EDGE_ENERGY = 1000.0;
    private static final short[] DELTAS = {-1, 0, 1};

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
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

    // DAG solution
    public int[] findVerticalSeam() {

        final int height = height();
        final int width = width();
        final double[][] energyMatrix = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energyMatrix[i][j] = energy(j, i);
            }
        }

        double[][] distTo = new double[height][width];
        int[][] edgeTo = new int[height][width];
        for (int i = 0; i < width; i++) {
            distTo[0][i] = energyMatrix[0][i];
        }

        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {

                double min = Double.MAX_VALUE;
                for (int k = 0; k < DELTAS.length; k++) {
                    int i_ = i - 1;
                    int j_ = j + DELTAS[k];
                    if (i_ < 0 || i_ >= height || j_ < 0 || j_ >= width) {
                        continue;
                    }

                    double dist = distTo[i_][j_] + energyMatrix[i][j];
                    if (dist < min) {
                        min = dist;
                        distTo[i][j] = min;
                        edgeTo[i][j] = j_;
                    }
                }
            }
        }

        int i = height - 1;
        int minIndex = 0;
        double min = distTo[i][minIndex];
        for (int j = 0; j < width; j++) {
            if (distTo[i][j] < min) {
                min = distTo[i][j];
                minIndex = j;
            }
        }

        int[] seam = new int[height];
        seam[height - 1] = minIndex;
        for (int j = height - 1; j > 0; j--) {
            minIndex = edgeTo[j][minIndex];
            seam[j - 1] = minIndex;
        }

        return seam;
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
            if (position < 0 || position >= seamRange) {
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