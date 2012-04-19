package com.dolfdijkstra.beacon.mapreduce;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    public long timestamp;
    public String uuid;
    public Map<String, String> headers;
    public Body body;
   
}
