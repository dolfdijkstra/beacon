package com.dolfdijkstra.beacon.mapreduce;

import java.io.IOException;
import java.io.Serializable;

import com.cloudera.crunch.CombineFn;
import com.cloudera.crunch.DoFn;
import com.cloudera.crunch.Emitter;
import com.cloudera.crunch.PCollection;
import com.cloudera.crunch.PTable;
import com.cloudera.crunch.Pair;
import com.cloudera.crunch.Pipeline;
import com.cloudera.crunch.impl.mr.MRPipeline;
import com.cloudera.crunch.type.writable.Writables;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

@SuppressWarnings("serial")
public class ElapsedByPage extends Configured implements Tool, Serializable {
    static enum COUNTERS {
        NO_MATCH, CORRUPT_SIZE
    }

   
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println();
            System.err.println("Two and only two arguments are accepted.");
            System.err.println("Usage: " + this.getClass().getName() + " [generic options] input output");
            System.err.println();
            GenericOptionsParser.printGenericCommandUsage(System.err);
            return 1;
        }
        // Create an object to coordinate pipeline creation and execution.
        Pipeline pipeline = new MRPipeline(ElapsedByPage.class, getConf());
        // Reference a given text file as a collection of Strings.
        PCollection<String> lines = pipeline.readTextFile(args[0]);

        // Combiner used for summing up response size
        CombineFn<String, Long> longSumCombiner = CombineFn.SUM_LONGS();

        // Table of (ip, sum(response size))
        PTable<String, Long> ipAddrResponseSize = lines
                .parallelDo(extractResponseTime, Writables.tableOf(Writables.strings(), Writables.longs()))
                .groupByKey().combineValues(longSumCombiner);

        pipeline.writeTextFile(ipAddrResponseSize, args[1]);
        // Execute the pipeline as a MapReduce.
        pipeline.done();
        return 0;
    }

    DoFn<String, Pair<String, Long>> extractResponseTime = new DoFn<String, Pair<String, Long>>() {
        transient JsonMapper mapper;

        public void initialize() {
            mapper = new JsonMapper();
        }

        public void process(String line, Emitter<Pair<String, Long>> emitter) {

            try {
                Data data = mapper.parse(line);
                String userAgent = data.headers.get("user-agent");
                NavigationTiming timing = data.body.timing;
                long start = timing.responseStart;
                long end = timing.domComplete;

                System.out.println("navigation took: " + (timing.loadEventEnd - timing.navigationStart));
                System.out.println("unload took: " + (timing.unloadEventEnd - timing.unloadEventStart));
                System.out.println("pre-fetch took: " + (timing.requestStart - timing.fetchStart));
                System.out.println("pre-request took: " + (timing.fetchStart - timing.navigationStart));
                System.out.println("domainLookup took: " + (timing.domainLookupEnd - timing.domainLookupStart));
                System.out.println("connect took: " + (timing.connectEnd - timing.connectStart));
                System.out.println("request took: " + (timing.responseStart - timing.requestStart));
                System.out.println("response took: " + (timing.responseEnd - timing.responseStart));
                System.out.println("pre-domLoading took: " + (timing.domLoading - timing.responseStart));
                //System.out.println("domInteractive after response took: " + (timing.domInteractive - timing.responseEnd));
                System.out.println("domInteractive took: " + (timing.domInteractive - timing.domLoading));

                System.out.println("pre-domContentLoadedEvent took: " + (timing.domContentLoadedEventStart - timing.domInteractive));

                System.out.println("domContentLoadedEvent took: " + (timing.domContentLoadedEventEnd - timing.domContentLoadedEventStart));
                System.out.println("domLoading took: " + (timing.domComplete - timing.domLoading));
                System.out.println("loadEvent took: " + (timing.loadEventEnd - timing.loadEventStart));

                System.out.println("pre-request took: " + (timing.requestStart - timing.navigationStart));
                System.out.println("server time took: " + (timing.responseEnd - timing.requestStart));
                System.out.println("browser time took: " + (timing.domInteractive - timing.responseStart));

                
                
                emitter.emit(Pair.of(userAgent, end - start));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    public static void main(String[] args) throws Exception {
        int ret = ToolRunner.run(new Configuration(), new ElapsedByPage(), args);
        System.exit(ret);
    }
}
