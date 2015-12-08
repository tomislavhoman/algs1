import edu.princeton.cs.algs4.MSD;

public class CircularSuffixArray {

    private int length;
    private int index[];
    private String string;

    // circular suffix array of s
    public CircularSuffixArray(String string) {

        if (string == null) {
            throw new NullPointerException();
        }

        this.length = string.length();
        this.index = new int[length];
        this.string = string;
        createArray();
    }

    private void createArray() {

        CSAElement[] elements = new CSAElement[length];
        for (int i = 0; i < length; i++) {
            elements[i] = new CSAElement(i);
        }

        lsdSort(elements);
//        Arrays.sort(elements);

        for (int i = 0; i < length; i++) {
            index[i] = elements[i].position;
        }
    }

    private void lsdSort(CSAElement[] elements) {
        int R = 256;   // extend ASCII alphabet size
        int N = length;
        int W = length;
        CSAElement[] aux = new CSAElement[N];

        for (int d = W - 1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++)
                count[getChar(elements, i, d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // move data
            for (int i = 0; i < N; i++)
                aux[count[getChar(elements, i, d)]++] = elements[i];

            // copy back
            for (int i = 0; i < N; i++)
                elements[i] = aux[i];
        }
    }

    private char getChar(CSAElement[] elements, int i, int d) {
        // i - Ith string in String[] if we use standard LSD
        // d - Dth char in Ith string if we use standard LSD
//         return a[i].charAt(d); - standard LSD way
        return string.charAt((d + elements[i].position) % length);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length) {
            throw new IndexOutOfBoundsException();
        }

        return index[i];
    }

    private class CSAElement implements Comparable<CSAElement> {
        private int position;

        private CSAElement(int position) {
            this.position = position;
        }

        @Override
        public int compareTo(CSAElement other) {

            int k = 0;
            while (k < length) {
                char c1 = string.charAt((position + k) % length);
                char c2 = string.charAt((other.position + k) % length);
                if (c1 != c2) {
                    return c1 - c2;
                }
                k++;
            }

            return 0;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
//        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");
    }
}