import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(final WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(final String[] nouns) {
        if (nouns == null) {
            throw new NullPointerException();
        }

        final int[] distances = new int[nouns.length];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = 0;
        }

        for (int i = 0; i < nouns.length; i++) {

            final String nounA = nouns[i];
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) {
                    continue;
                }

                distances[i] += wordNet.distance(nounA, nouns[j]);
            }
        }

        int max = distances[0];
        int indexOfMax = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] > max) {
                max = distances[i];
                indexOfMax = i;
            }
        }

        return nouns[indexOfMax];
    }

    // see test client below
    public static void main(String[] args) {
        final WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        final Outcast outcast = new Outcast(wordnet);
        In in = new In("wordnet/outcast11.txt");
        String[] nouns = in.readAllStrings();
        StdOut.println(outcast.outcast(nouns));
    }
}
