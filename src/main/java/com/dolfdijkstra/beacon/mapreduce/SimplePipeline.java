package com.dolfdijkstra.beacon.mapreduce;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class SimplePipeline {

    public static void main(String[] args) throws IOException {

        String name = "beacon-json.log";

        InputStream input;

        input = FileUtils.openInputStream(new File(name));
        JsonMapper m = new JsonMapper();
        print("year", "month", "weekday", "hour", "bdoctype", "adoctype", "ahead", "abody", "ahtml", "images",
                "domcount", "navigation", "prerequest", "servertime", "browsertime");
        for (String line : IOUtils.readLines(input)) {
            try {
                Data data = m.parse(line);
                NavigationTimingOrder sorter = new NavigationTimingOrder();

                Pair[] sorted = sorter.order(data.body.timing);

                // System.out.println(StringUtils.join());
                NavigationTiming timing = data.body.timing;

                TimingsTuple t = new TimingsTuple();
                t.timestamp = data.timestamp;
                t.location = data.body.location;
                t.userAgent = data.body.userAgent;
                t.title = data.body.title;
                t.referrer = data.body.referrer;
                t.domCount = data.body.domCount;
                t.timings = sorted;
                t.navigation = timing.loadEventEnd - timing.navigationStart;
                t.preRequest = timing.requestStart - timing.navigationStart;
                t.serverTime = timing.responseEnd - timing.requestStart;
                t.browserTime = timing.domComplete - timing.domLoading;
                t.timeToFirstByte = timing.domLoading - timing.requestStart;
                print(t);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(line);
            }
        }

    }

    private static Pair[] parseQS(String query) {
        String[] parts = query.split("&");
        Pair[] p = new Pair[parts.length];
        int i = 0;
        for (String part : parts) {
            String[] w = part.split("=");
            p[i] = new Pair(w[0], Long.parseLong(w[1]));
            i++;
        }
        return p;
    }

    static void print(TimingsTuple t) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t.timestamp);
        URI u = URI.create(t.location);
        Pair[] pairs = parseQS(u.getQuery());

        print(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_WEEK),
                cal.get(Calendar.HOUR_OF_DAY), pairs[0].value, pairs[1].value, pairs[2].value, pairs[3].value,
                pairs[4].value, pairs.length == 5 ? 0 : pairs[5].value, t.domCount, t.navigation, t.preRequest,
                t.serverTime, t.browserTime, t.timeToFirstByte);

    }

    static void print(Object... a) {
        int iMax = a.length - 1;
        if (iMax == -1)
            return;
        StringBuilder b = new StringBuilder();

        for (int i = 0; i <= iMax; i++) {
            b.append(String.valueOf(a[i]));
            if (i != iMax)
                b.append(", ");
        }
        System.out.println(b.toString());
    }

}
