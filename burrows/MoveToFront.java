import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static char[] alphabet;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        initAlphabet();
        while (!BinaryStdIn.isEmpty()) {
            encodeChar(BinaryStdIn.readChar());
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        initAlphabet();
        while (!BinaryStdIn.isEmpty()) {
            decodeChar(BinaryStdIn.readInt(8));
        }
        BinaryStdOut.close();
    }

    private static void initAlphabet() {
        alphabet = new char[256];
        for (char i = 0; i < alphabet.length; i++) {
            alphabet[i] = i;
        }
    }

    private static char findChar(char c) {
        for (char i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return '\0';
    }

    private static void putToFront(int position) {
        char c = alphabet[position];
        for (int i = position; i >= 1; i--) {
            alphabet[i] = alphabet[i - 1];
        }
        alphabet[0] = c;
    }

    private static void encodeChar(char c) {
        char position = findChar(c);
        BinaryStdOut.write(position);
        putToFront(position);
    }

    private static void decodeChar(int position) {
        char c = alphabet[position];
        BinaryStdOut.write(c);
        putToFront(position);
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        String c = args[0];
        if ("-".equals(c)) {
            encode();
        } else if ("+".equals(c)) {
            decode();
        } else {
            throw new IllegalArgumentException();
        }

//        for (char c : "ABRACADABRA!".toCharArray()) {
//            encodeChar(c);
//        }
    }
}