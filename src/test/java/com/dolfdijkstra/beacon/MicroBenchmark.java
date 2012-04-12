package com.dolfdijkstra.beacon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class MicroBenchmark {
    String[] a;

    @Test
    public void testBench() throws IOException {
        initArray(50000);
        writeSize();

        for (int i = 1; i <= 34000; i = i * 2)
            performTest(i);
    }

    /**
     * @throws IOException
     */
    private void writeSize() throws IOException {
        ByteArrayOutputStream  b = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(b);
        os.writeObject(a);
        os.close();
        System.out.println(b.size());
    }

    /**
     * 
     */
    private void performTest(int d) {

        long c = 0;
        long t = System.nanoTime();
        for (int i = 0; i < d; i++) {
            c += iteration();
        }
        long t1 = System.nanoTime();
        System.out.println(c);
        System.out.println(d + " iterations in " + (t1 - t) / 1000000);
    }

    /**
     * 
     */
    private long iteration() {
        long c = 0;
        for (int i = 0; i < a.length; i++) {
            c += a[i].length();
        }
        return c;
    }

    /**
     * 
     */
    private void initArray(int c) {
        a = new String[c];
        String prefix = "foo";
        for (int s = 0; s < 50; s++) {
            prefix += Character.toChars(s + 34);
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = prefix + Long.toBinaryString(i);
        }
    }

}
