package com.dolfdijkstra.beacon.mapreduce;

import java.io.IOException;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonMapper {
    private ObjectMapper mapper = new ObjectMapper();

    public Data parse(String line) throws JsonParseException, JsonMappingException, IOException {
        Data data = mapper.readValue(line, Data.class);
        return data;

    }

}
