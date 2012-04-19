package com.dolfdijkstra.beacon.mapreduce;

import java.lang.reflect.Field;

public class NavigationTimingOrder {

    private static Pair[] sort(Pair y[], final int len) {
        Pair[] x;
        if (len < y.length) {
            x = resize(y, len);
        } else {
            x = y;
        }

        final int off = 0;

        for (int i = off; i < len + off; i++) {
            for (int j = i; j > off && x[j - 1].value > x[j].value; j--) {
                swap(x, j, j - 1);
            }
        }
        return x;
    }

    private static Pair[] resize(Pair[] y, final int len) {
        Pair[] n = new Pair[len];
        System.arraycopy(y, 0, n, 0, len);
        return n;
    }

    private static void swap(Pair x[], int a, int b) {
        Pair t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    Pair[] order(NavigationTiming nav) {
        Field[] fields = nav.getClass().getFields();
        Pair[] l = new Pair[fields.length];
        int j = 0;
        for (int i = 0; i < fields.length; i++) {
            long val;
            try {
                val = fields[i].getLong(nav);
                if (val > 0) {
                    l[j++] = new Pair(fields[i].getName(), val);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        Pair[] s = sort(l, j);
        return rebase(s);

    }

    private Pair[] rebase(Pair[] pairs) {
        long b = pairs[0].value;
        for (Pair p : pairs) {
            p.value = p.value - b;
        }
        return pairs;
    }
}
