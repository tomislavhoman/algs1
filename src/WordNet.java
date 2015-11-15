import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

public class WordNet {

    private final ST<String, Bag<Integer>> wordsToIds;
    private final ST<Integer, String> idsToSynsets;
    private final Digraph wordNetGraph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(final String synsets, final String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException();
        }

        // Build symbol table that will map words to synset ids - word can map to more ids
        // Build a symbol table that will map id to synset
        // Build a digraph of synset ids
        wordsToIds = new ST<>();
        idsToSynsets = new ST<>();

        final In input1 = new In(synsets);
        while (input1.hasNextLine()) {
            final String[] line = input1.readLine().split(",");
            final Integer id = Integer.parseInt(line[0]);
            final String synset = line[1];
            final String[] words = synset.split(" ");
            idsToSynsets.put(id, synset);
            for (String word : words) {
                Bag<Integer> ids;
                if (wordsToIds.contains(word)) {
                    ids = wordsToIds.get(word);
                } else {
                    ids = new Bag<>();
                    wordsToIds.put(word, ids);
                }
                ids.add(id);
            }
        }
        input1.close();

        wordNetGraph = new Digraph(idsToSynsets.size());
        final In input2 = new In(hypernyms);
        while (input2.hasNextLine()) {
            final String[] line = input2.readLine().split(",");
            final Integer v = Integer.parseInt(line[0]);
            if (line.length > 1) {
                for (int i = 1; i < line.length; i++) {
                    final Integer w = Integer.parseInt(line[i]);
                    wordNetGraph.addEdge(v, w);
                }
            }
        }
        input2.close();

        if (!isRooted(wordNetGraph)) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(wordNetGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordsToIds.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(final String word) {
        if (word == null) {
            throw new NullPointerException();
        }

        return wordsToIds.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(final String nounA, final String nounB) {
        checkIfValidNoun(nounA);
        checkIfValidNoun(nounB);

        return sap.length(wordsToIds.get(nounA), wordsToIds.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(final String nounA, final String nounB) {
        checkIfValidNoun(nounA);
        checkIfValidNoun(nounB);

        final int common = sap.ancestor(wordsToIds.get(nounA), wordsToIds.get(nounB));
        if (common == -1) {
            return "";
        }
        return idsToSynsets.get(common);
    }

    private void checkIfNull(final String string) {
        if (string == null) {
            throw new NullPointerException();
        }
    }

    private void checkIfValidNoun(final String word) {
        checkIfNull(word);
        if (!isNoun(word)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isRooted(Digraph graph) {
        int numberOfRoots = 0;
        for (int v = 0; v < graph.V(); v++) {
            if (graph.outdegree(v) == 0) {
                numberOfRoots++;
            }
        }

        final DirectedCycle cycle = new DirectedCycle(graph);
        return numberOfRoots == 1 && !cycle.hasCycle();
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        final WordNet wordNet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
//        final String entity = "entity";
//        final boolean entityIsAWord = wordNet.isNoun(entity);
//        final boolean foobarIsNotAWord = wordNet.isNoun("foobar");
//        final Bag<Integer> entityIds = wordNet.wordsToIds.get(entity);
//        for (int id : entityIds) {
//            final String synset = wordNet.idsToSynsets.get(id);
//            int a = 4;
//        }
//
//        final String commonSynset1 = wordNet.sap("Afro", "European");
    }
}