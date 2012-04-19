package com.dolfdijkstra.beacon.mapreduce;

class TimingsTuple {
    public long timestamp;
    public String location;
    public String userAgent;
    public String title;
    public String referrer;
    public int domCount;
    public Pair[] timings;

    public long navigation;
    public long preRequest;
    public long serverTime;
    public long browserTime;

    public long timeToFirstByte;
    public long domContentLoadedEvent;
    public long loadEvent;

}