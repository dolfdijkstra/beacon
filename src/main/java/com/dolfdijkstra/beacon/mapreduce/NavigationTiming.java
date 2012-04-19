package com.dolfdijkstra.beacon.mapreduce;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NavigationTiming {
    public long navigationStart;

    public long unloadEventStart;
    public long unloadEventEnd;

    public long redirectStart;
    public long redirectEnd;

    public long fetchStart;

    public long domainLookupStart;
    public long domainLookupEnd;

    public long connectStart;
    public long connectEnd;

    public long requestStart;
    public long responseStart;
    public long responseEnd;

    public long domLoading;
    public long domInteractive;

    public long domContentLoadedEventStart;
    public long domContentLoadedEventEnd;

    public long domComplete;

    public long loadEventStart;
    public long loadEventEnd;

}
