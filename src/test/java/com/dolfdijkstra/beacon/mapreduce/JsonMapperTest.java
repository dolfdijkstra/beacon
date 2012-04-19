package com.dolfdijkstra.beacon.mapreduce;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonMapperTest {

    @Test
    public void testParse() throws IOException {

        String name = getClass().getPackage().getName().replace('.', '/') + "/test-data.txt";

        InputStream input;

        input = getClass().getClassLoader().getResourceAsStream(name);
        JsonMapper m = new JsonMapper();
        for (String line : IOUtils.readLines(input)) {
            Data data = m.parse(line);
            assertEquals("574df0a9-1342-4a31-8363-039dc20c0213", data.uuid);
            assertEquals(
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.152 Safari/535.19",
                    data.headers.get("user-agent"));
            long s = data.body.timing.navigationStart;
            assertEquals(1334345931091L, s);
            NavigationTimingOrder sorter = new NavigationTimingOrder();

            Pair[] sorted = sorter.order(data.body.timing);
            for (int i = 1; i < sorted.length; i++) {
                System.out.println(sorted[i].name + ": " + (sorted[i].value - sorted[i - 1].value));
            }

            printAsMatrix(sorted);

            // System.out.println(StringUtils.join());
            NavigationTiming timing = data.body.timing;

            System.out.println("navigation took: " + (timing.loadEventEnd - timing.navigationStart));
            System.out.println("unload took: " + (timing.unloadEventEnd - timing.unloadEventStart));
            System.out.println("pre-fetch took: " + (timing.requestStart - timing.fetchStart));
            System.out.println("pre-request took: " + (timing.fetchStart - timing.navigationStart));
            System.out.println("domainLookup took: " + (timing.domainLookupEnd - timing.domainLookupStart));
            System.out.println("connect took: " + (timing.connectEnd - timing.connectStart));
            System.out.println("request took: " + (timing.responseStart - timing.requestStart));
            System.out.println("response took: " + (timing.responseEnd - timing.responseStart));
            System.out.println("pre-domLoading took: " + (timing.domLoading - timing.responseStart));
            // System.out.println("domInteractive after response took: " +
            // (timing.domInteractive - timing.responseEnd));
            System.out.println("domInteractive took: " + (timing.domInteractive - timing.domLoading));

            System.out.println("pre-domContentLoadedEvent took: "
                    + (timing.domContentLoadedEventStart - timing.domInteractive));

            System.out.println("domLoading took: " + (timing.domComplete - timing.domLoading));
            System.out.println();
            System.out.println("navigation took: " + (timing.loadEventEnd - timing.navigationStart));
            System.out.println("pre-request took: " + (timing.requestStart - timing.navigationStart));
            System.out.println("server time took: " + (timing.responseEnd - timing.requestStart));
            System.out.println("browser time took: " + (timing.domComplete - timing.domLoading));
            System.out.println();
            System.out.println("time to first byte time took: " + (timing.domLoading - timing.requestStart));

            System.out.println("domContentLoadedEvent took: "
                    + (timing.domContentLoadedEventEnd - timing.domContentLoadedEventStart));

            System.out.println("loadEvent took: " + (timing.loadEventEnd - timing.loadEventStart));
            TimingsTuple t = new TimingsTuple();
            t.timestamp = data.timestamp;
            t.location = data.body.location;
            t.userAgent = data.body.userAgent;
            t.title = data.body.title;
            t.referrer = data.body.referrer;
            t.domCount = data.body.domCount;
            t.timings = sorted;
            t.browserTime = timing.domComplete - timing.domLoading;
            t.serverTime = timing.responseEnd - timing.requestStart;
            t.navigation = timing.loadEventEnd - timing.navigationStart;
            t.preRequest = timing.requestStart - timing.navigationStart;
            print(t.timestamp, t.location, t.title, t.domCount);
        }

    }

    void print(Object... a) {
        System.out.println(Arrays.toString(a));
    }

    /**
     * @param sorted
     */
    private void printAsMatrix(Pair[] sorted) {
        for (Pair p : sorted) {
            System.out.println(p.name + ":" + p.value);
        }
        int maxLength = 0;
        for (Pair p : sorted) {
            maxLength = Math.max(maxLength, p.name.length());
        }

        long[][] matrix = new long[sorted.length][sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            for (int j = 0; j < sorted.length; j++) {
                matrix[i][j] = sorted[j].value - sorted[i].value;

            }
        }
        System.out.print(StringUtils.repeat(" ", maxLength + 2));
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(StringUtils.leftPad(sorted[i].name, sorted[i].name.length() + 2));
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {

            System.out.print(StringUtils.leftPad(sorted[i].name, maxLength + 2));
            for (int j = 0; j < matrix[i].length; j++) {
                if (i < j /* matrix[i][j] >= 0 */)
                    System.out.print(StringUtils.leftPad(Long.toString(matrix[i][j]), sorted[j].name.length() + 2));
                else if (i == j)
                    System.out.print(StringUtils.leftPad("x", sorted[j].name.length() + 2));
                else
                    System.out.print(StringUtils.repeat(" ", sorted[j].name.length() + 2));

            }
            System.out.println();
        }
    }
}
