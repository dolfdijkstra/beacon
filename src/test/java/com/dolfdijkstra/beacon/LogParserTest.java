package com.dolfdijkstra.beacon;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogParserTest {

    @Test
    public void testParseLine() throws JsonParseException, JsonMappingException, IOException {
        
        String line = "{\"navigationStart\":1329584632137,\"unloadEventStart\":1329584632251,\"unloadEventEnd\":1329584632301,\"redirectStart\":0,\"redirectEnd\":0,\"fetchStart\":1329584632186,\"domainLookupStart\":1329584632186,\"domainLookupEnd\":1329584632186,\"connectStart\":1329584632186,\"connectEnd\":1329584632186,\"requestStart\":1329584632186,\"responseStart\":1329584632215,\"responseEnd\":1329584632404,\"domLoading\":1329584632251,\"domInteractive\":1329584632975,\"domContentLoadedEventStart\":1329584633024,\"domContentLoadedEventEnd\":1329584633157,\"domComplete\":1329584633210,\"loadEventStart\":1329584633210,\"loadEventEnd\":1329584633285,\"type\":0,\"redirectCount\":0,\"top\":0,\"height\":1080,\"width\":1920,\"left\":0,\"pixelDepth\":24,\"colorDepth\":24,\"availWidth\":1920,\"availHeight\":1040,\"availLeft\":0,\"availTop\":0,\"location\":\"http://cos-cs-del:8080/cs/hello2.html\",\"userAgent\":\"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.1) Gecko/20100101 Firefox/10.0.1\",\"title\":\"Hello Links\",\"innerHeight\":479,\"innerWidth\":1920,\"outerHeight\":1056,\"outerWidth\":1936,\"pageXOffset\":0,\"pageYOffset\":0,\"screenX\":-8,\"screenY\":-8,\"referrer\":\"http://cos-cs-del:8080/cs/hello2.html\"}";
        LogParser parser = new LogParser(null);
        parser.parseLine(line);
        
        
        
    }

}
