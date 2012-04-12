package com.dolfdijkstra.beacon;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class LogParser {
    private BufferedReader reader;

    class ValueComparator implements Comparator<String> {

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

    private final TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {

    };

    /**
     * @param reader
     */
    public LogParser(BufferedReader reader) {
        super();
        this.reader = reader;
    }

    private ObjectMapper mapper = new ObjectMapper();

    public void parse() throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.isNotBlank(line)) {
                parseLine(line);
            }
        }
    }

    void sort(Map<String, Long> map) {

        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Long> sorted_map = new TreeMap<String, Long>(bvc);

        sorted_map.putAll(map);

        System.out.println("results");
        long prev = -1;
        for (Entry<String, Long> e : sorted_map.entrySet()) {
            Long val = e.getValue();
            String key = e.getKey();

            System.out.println(key + "/" + (prev > 0 ? Long.toString(val - prev) : Long.toString(val)));
            prev = val;
        }

    }

    void parseLine(String line) throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> json = mapper.readValue(line, typeRef);
        Map<String, Long> events = new HashMap<String, Long>();

        for (Entry<String, Object> e : json.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue() + " " + e.getValue().getClass().getSimpleName());
            if (e.getValue() instanceof Long)
                events.put(e.getKey(), (Long) e.getValue());
        }
        sort(events);
        Long navigationStart = (Long) json.get("navigationStart");

        Long unloadEventStart = (Long) json.get("unloadEventStart");
        Long unloadEventEnd = (Long) json.get("unloadEventEnd");

        Number redirectStart = (Number) json.get("redirectStart");
        Number redirectEnd = (Number) json.get("redirectEnd");

        Long fetchStart = (Long) json.get("fetchStart");

        Long domainLookupStart = (Long) json.get("domainLookupStart");
        Long domainLookupEnd = (Long) json.get("domainLookupEnd");

        Long connectStart = (Long) json.get("connectStart");
        Long connectEnd = (Long) json.get("connectEnd");

        Long requestStart = (Long) json.get("requestStart");
        Long responseStart = (Long) json.get("responseStart");
        Long responseEnd = (Long) json.get("responseEnd");

        Long domLoading = (Long) json.get("domLoading");
        Long domInteractive = (Long) json.get("domInteractive");

        Long domContentLoadedEventStart = (Long) json.get("domContentLoadedEventStart");
        Long domContentLoadedEventEnd = (Long) json.get("domContentLoadedEventEnd");

        Long domComplete = (Long) json.get("domComplete");

        Long loadEventStart = (Long) json.get("loadEventStart");
        Long loadEventEnd = (Long) json.get("loadEventEnd");

        System.out.println("navigation took: " + (loadEventEnd - navigationStart));
        System.out.println("unload took: " + (unloadEventEnd - unloadEventStart));
        System.out.println("pre-fetch took: " + (requestStart - fetchStart));
        System.out.println("pre-request took: " + (fetchStart - navigationStart));
        System.out.println("domainLookup took: " + (domainLookupEnd - domainLookupStart));
        System.out.println("connect took: " + (connectEnd - connectStart));
        System.out.println("request took: " + (responseStart - requestStart));
        System.out.println("response took: " + (responseEnd - responseStart));
        System.out.println("pre-domLoading took: " + (domLoading - responseStart));
        System.out.println("domInteractive after response took: " + (domInteractive - responseEnd));
        System.out.println("domInteractive took: " + (domInteractive - domLoading));

        System.out.println("pre-domContentLoadedEvent took: " + (domContentLoadedEventStart - domInteractive));

        System.out.println("domContentLoadedEvent took: " + (domContentLoadedEventEnd - domContentLoadedEventStart));
        System.out.println("domLoading took: " + (domComplete - domLoading));
        System.out.println("loadEvent took: " + (loadEventEnd - loadEventStart));

        System.out.println("pre-request took: " + (requestStart - navigationStart));
        System.out.println("server time took: " + (responseEnd - requestStart));
        System.out.println("browser time took: " + (domInteractive - responseStart));

    }

}
