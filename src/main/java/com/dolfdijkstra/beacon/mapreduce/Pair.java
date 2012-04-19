package com.dolfdijkstra.beacon.mapreduce;

public class Pair {
    public String name;
    public long value;

    /**
     * @param name
     * @param value
     */
    public Pair(String name, long value) {
        super();
        this.name = name;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[name=" + name + ", value=" + value + "]";
    }
}
