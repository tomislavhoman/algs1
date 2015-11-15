import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

// Got 87%
public class BaseballElimination {

    private int N;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private ST<String, Integer> namesToIndex;
    private String[] indexToNames;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        checkString(filename);
        initData(filename);
    }

    private void initData(String filename) {
        In in = new In(filename);
        this.N = in.readInt();

        this.w = new int[N];
        this.l = new int[N];
        this.r = new int[N];
        this.g = new int[N][N];
        this.namesToIndex = new ST<>();
        this.indexToNames = new String[N];

        for (int i = 0; i < N; i++) {
            String name = in.readString();
            namesToIndex.put(name, i);
            indexToNames[i] = name;
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < N; j++) {
                g[i][j] = in.readInt();
            }
        }
    }

    private void checkString(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
    }

    private void checkTeamName(String teamName) {
        checkString(teamName);
        if (!namesToIndex.contains(teamName)) {
            throw new IllegalArgumentException();
        }
    }

    // number of teams
    public int numberOfTeams() {
        return N;
    }

    // all teams
    public Iterable<String> teams() {
        return namesToIndex.keys();
    }

    // number of wins for given team
    public int wins(String team) {
        checkTeamName(team);
        return w[namesToIndex.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        checkTeamName(team);
        return l[namesToIndex.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        checkTeamName(team);
        return r[namesToIndex.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        checkTeamName(team1);
        checkTeamName(team2);
        return g[namesToIndex.get(team1)][namesToIndex.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        checkTeamName(team);
        if (isTriviallyEliminated(team)) {
            return true;
        }

        return calculateEliminators(team).size() > 0;
    }

    private boolean isTriviallyEliminated(String team) {
        int possibleWins = wins(team) + remaining(team);
        for (String opponent : teams()) {
            if (opponent.equals(team)) {
                continue;
            }

            if (wins(opponent) > possibleWins) {
                return true;
            }
        }
        return false;
    }

    private FordFulkerson calculateFordFulkerson(int numberOfVertices,
                                                 int x,
                                                 int source,
                                                 int target,
                                                 int numberOfGameCombinations) {
        FlowNetwork flowNetwork = new FlowNetwork(numberOfVertices);
        int indexOfGame = 1; //0 is source
        for (int i = 0; i < N; i++) {
            if (i == x) {
                continue;
            }

            for (int j = i; j < N; j++) {
                if (j == x || i == j) {
                    continue;
                }

                int Gij = g[i][j];
                flowNetwork.addEdge(new FlowEdge(source, indexOfGame, Gij));
                int Ti = i + numberOfGameCombinations + 1;
                if (Ti >= x) {
                    Ti--;
                }
                int Tj = j + numberOfGameCombinations + 1;
                if (Tj >= x) {
                    Tj--;
                }
                flowNetwork.addEdge(new FlowEdge(indexOfGame, Ti, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(indexOfGame, Tj, Double.POSITIVE_INFINITY));
                indexOfGame++;
            }
        }

        int possibleWinsForX = w[x] + r[x];
        for (int i = 0; i < N - 1; i++) {
            if (i == x) {
                continue;
            }

            int Ti = i + numberOfGameCombinations + 1;
            if (Ti >= x) {
                Ti--;
            }
            int possibleWinsForI = possibleWinsForX - w[i];
            if (possibleWinsForI < 0) {
                possibleWinsForI = 0;
            }
            flowNetwork.addEdge(new FlowEdge(Ti, target, possibleWinsForI));
        }

        return new FordFulkerson(flowNetwork, source, target);
    }

    private Queue<String> calculateEliminators(String team) {
        //All the combination of games + teams that are not "team" + source and sink
        int numberOfGameCombinations = (N - 1) * (N - 2) / 2;
        int numberOfVertices = numberOfGameCombinations + N - 1 + 2;
        int source = 0;
        int target = numberOfVertices - 1;
        int x = namesToIndex.get(team);

        FordFulkerson fordFulkerson = calculateFordFulkerson(numberOfVertices, x, source, target, numberOfGameCombinations);

        Queue<String> eliminatedBy = new Queue<>();
        for (int i = 0; i < N - 1; i++) {
            if (i == x) {
                continue;
            }

            int Ti = i + numberOfGameCombinations + 1;
            if (Ti >= x) {
                Ti--;
            }
            if (fordFulkerson.inCut(Ti)) {
                eliminatedBy.enqueue(indexToNames[i]);
            }
        }

        return eliminatedBy;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        checkTeamName(team);
        if (isTriviallyEliminated(team)) {
            return calculateTrivialEliminators(team);
        }
        return calculateEliminators(team);
    }

    private Iterable<String> calculateTrivialEliminators(String team) {
        Queue<String> eliminators = new Queue<>();
        int x = namesToIndex.get(team);
        int possibleWins = w[x] + r[x];
        for (int i = 0; i < N; i++) {
            if (i == x) {
                continue;
            }

            if (w[i] > possibleWins) {
                eliminators.enqueue(indexToNames[i]);
                break;
            }
        }
        return eliminators;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
