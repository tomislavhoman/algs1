package com.homan.algs4.sort;

import java.util.Random;

public final class KnuthShuffle extends Sort {

    public static void shuffle(Comparable[] items) {

        Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            int rand = random.nextInt(i + 1);
            exch(items, i, rand);
        }
    }
}
