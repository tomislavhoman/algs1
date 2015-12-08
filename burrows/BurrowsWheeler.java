import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {

        StringBuilder stringBuilder = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            stringBuilder.append(BinaryStdIn.readChar());
        }

        String string = stringBuilder.toString();
        CircularSuffixArray csa = new CircularSuffixArray(string);
        int first = -1;
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                first = i;
                break;
            }
        }
        BinaryStdOut.write(first, 32);
        int length = csa.length();
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(string.charAt((csa.index(i) + length - 1) % length));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {

        if (BinaryStdIn.isEmpty()) {
            return;
        }

        int first = BinaryStdIn.readInt(32);
        StringBuilder stringBuilder = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            stringBuilder.append(BinaryStdIn.readChar(8));
        }

        String t = stringBuilder.toString();
        int length = t.length();
        char[] sorted = Arrays.copyOf(t.toCharArray(), length);
        Arrays.sort(sorted);
        int[] next = new int[length];

        int last = 0;
        for (int i = 0; i < length; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                last = next[i - 1] + 1;
            } else {
                last = 0;
            }
            next[i] = t.indexOf(sorted[i], last);
        }

        int current = first;
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(sorted[current]);
            current = next[current];
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        String c = args[0];
        if ("-".equals(c)) {
            encode();
        } else if ("+".equals(c)) {
            decode();
        } else {
            throw new IllegalArgumentException();
        }
    }
}