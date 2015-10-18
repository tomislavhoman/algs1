import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }

        final int k = Integer.parseInt(args[0]);
        final RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            queue.enqueue(string);
        }

        int i = 0;
        for (String item : queue) {
            if (++i > k) {
                break;
            }
            System.out.println(item);
        }
    }
}
