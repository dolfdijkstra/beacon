package com.dolfdijkstra.beacon.mapreduce;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {
    public NavigationTiming timing;
    public Map<String, Long> navigation;
   
    public Map<String, Object> screen;
    
    public String location;
    public String userAgent;
    public String title;
    public int innerHeight;
    public int innerWidth;
    public int outerHeight;
    public int outerWidth;
    public int pageXOffset;
    public int pageYOffset;
    public int screenLeft;
    public int screenTop;
    public int screenX;
    public int screenY;
    public String referrer;
    public int domCount;
}
