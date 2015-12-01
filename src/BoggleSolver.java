import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private static final int[][] DELTAS = new int[][]{
            {-1, -1},
            {0, -1},
            {1, -1},
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1},
            {-1, 0}
    };

    private TrieNode trieRoot;
    private boolean[][] visited;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            putToTrie(word, calculateScoreForWord(word));
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        SET<String> words = new SET<>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                visited = new boolean[board.rows()][board.cols()];
                char c = board.getLetter(i, j);
                String word = String.valueOf(c);
                if (c == 'Q') {
                    word = word.concat("U");
                }
                dfsNonR(i, j, word, words, board);
            }
        }

        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (trieContains(word)) {
            return getFromTrie(word);
        } else {
            return 0;
        }
    }

    private void putToTrie(String word, int value) {
        trieRoot = putToTrie(trieRoot, word, value, 0);
    }

    private TrieNode putToTrie(TrieNode node, String word, int value, int depth) {

        if (node == null) {
            node = new TrieNode();
        }

        if (depth == word.length()) {
            node.isAWord = true;
            node.value = value;
            return node;
        }

        char c = word.charAt(depth);
        int index = c - 'A';
        node.children[index] = putToTrie(node.children[index], word, value, depth + 1);
        return node;
    }

//    private int getFromTrie(String word) {
//        TrieNode node = getFromTrie(trieRoot, word, 0);
//        if (node == null || !node.isAWord) {
//            return -1;
//        }
//
//        return node.value;
//    }

    private int getFromTrie(String word) {

        // Using non-recursive version for performance
        TrieNode node = trieRoot;
        int index = 0;
        int length = word.length();
        while (node != null && index <= length) {
            if (index == length) {
                if (node.isAWord) {
                    return node.value;
                } else {
                    return -1;
                }
            }

            int childIndex = word.charAt(index++) - 'A';
            node = node.children[childIndex];
        }

        return -1;
    }

//    private TrieNode getFromTrie(TrieNode node, String word, int depth) {
//        if (node == null || depth > word.length()) {
//            return null;
//        }
//
//        if (depth == word.length()) {
//            if (node.isAWord) {
//                return node;
//            } else {
//                return null;
//            }
//        }
//
//        int index = word.charAt(depth) - 'A';
//        return getFromTrie(node.children[index], word, depth + 1);
//
//    }

//    private boolean trieContains(String word) {
//        TrieNode node = getFromTrie(trieRoot, word, 0);
//        return node != null && node.isAWord;
//    }

    private boolean trieContains(String word) {

        // Using non-recursive version for performance
        return getFromTrie(word) > -1;
    }

    private boolean trieContainsPrefix(String prefix) {

        // Using non-recursive version for performance
        TrieNode node = trieRoot;
        int index = 0;
        while (node != null && index <= prefix.length()) {
            if (index == prefix.length()) {
                return true;
            }

            int childIndex = prefix.charAt(index++) - 'A';
            node = node.children[childIndex];
        }

        return false;
    }

//    private boolean trieContainsPrefix(String prefix) {
//        return trieContainsPrefix(trieRoot, prefix, 0) != null;
//    }

//    private TrieNode trieContainsPrefix(TrieNode node, String prefix, int depth) {
//        if (node == null || depth > prefix.length()) {
//            return null;
//        }
//
//        if (depth == prefix.length()) {
//            return node;
//        }
//
//        int index = prefix.charAt(depth) - 'A';
//        return trieContainsPrefix(node.children[index], prefix, depth + 1);
//    }

    private int calculateScoreForWord(String word) {
        int length = word.length();
        if (length <= 2) {
            return 0;
        } else if (length <= 4) {
            return 1;
        } else if (length == 5) {
            return 2;
        } else if (length == 6) {
            return 3;
        } else if (length == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    private void dfsNonR(int i1, int j1, String word1, SET<String> words, BoggleBoard board) {

        // Using non-recursive version for performance
        Stack<StackNode> stack = new Stack<>();
        stack.push(new StackNode(i1, j1, word1));

        while (!stack.isEmpty()) {

            StackNode node = stack.pop();
            int i = node.i;
            int j = node.j;
            String word = node.word;

            if (node.goingUp) {
                visited[i][j] = false;
                continue;
            }

            if (visited[i][j]) {
                continue;
            }

            node.goingUp = true;
            stack.push(node);

            visited[i][j] = true;
            if (trieContains(word) && word.length() > 2) {
                words.add(word);
            }

            for (int[] delta : DELTAS) {
                int i_ = delta[1] + i;
                int j_ = delta[0] + j;
                if (i_ < 0 || j_ < 0 || i_ > board.rows() - 1 || j_ > board.cols() - 1) {
                    continue;
                }

                if (!visited[i_][j_]) {
                    char c = board.getLetter(i_, j_);
                    String suffix = String.valueOf(c);
                    if (c == 'Q') {
                        suffix = suffix.concat("U");
                    }
                    String newWord = word.concat(suffix);
                    if (trieContainsPrefix(newWord)) {
                        stack.push(new StackNode(i_, j_, newWord));
                    }
                }
            }
        }
    }

    //97.22% for recursive solution
    private void dfs(int i, int j, String word, SET<String> words, BoggleBoard board) {
        if (visited[i][j]) {
            return;
        }

        visited[i][j] = true;
        if (trieContains(word) && word.length() > 2) {
            words.add(word);
        }

        for (int[] delta : DELTAS) {
            int i_ = delta[1] + i;
            int j_ = delta[0] + j;
            if (i_ < 0 || j_ < 0 || i_ > board.rows() - 1 || j_ > board.cols() - 1) {
                continue;
            }

            if (!visited[i_][j_]) {
                char c = board.getLetter(i_, j_);
                String suffix = String.valueOf(c);
                if (c == 'Q') {
                    suffix = suffix.concat("U");
                }
                String newWord = word.concat(suffix);
                if (trieContainsPrefix(newWord)) {
                    dfs(i_, j_, newWord, words, board);
                }
            }
        }

        visited[i][j] = false;
    }

    private static class TrieNode {
        private boolean isAWord;
        private int value;
        private TrieNode[] children;

        public TrieNode() {
            this.isAWord = false;
            this.children = new TrieNode[26];
        }
    }

    private static class StackNode {
        private int i;
        private int j;
        private String word;
        private boolean goingUp = false;

        public StackNode(int i, int j, String word) {
            this.i = i;
            this.j = j;
            this.word = word;
        }
    }

    public static void main(String[] args) {

//        String dictionaryFile = "boggle/dictionary-algs4.txt";
//        String boardFile = "boggle/board4x4.txt";

        String dictionaryFile = "boggle/dictionary-algs4.txt";
        String boardFile = "boggle/board-q.txt";

        In in = new In(dictionaryFile);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(boardFile);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
