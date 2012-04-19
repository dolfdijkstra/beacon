package com.dolfdijkstra.beacon;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<String> {

    final Map<String, Long> base;

    public ValueComparator(Map<String, Long> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        int i = base.get(a).compareTo(base.get(b));
        if (i == 0) {
            return a.compareTo(b);
        } else {
            return i;
        }
    }
}