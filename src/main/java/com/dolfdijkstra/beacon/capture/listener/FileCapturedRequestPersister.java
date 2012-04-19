package com.dolfdijkstra.beacon.capture.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import com.dolfdijkstra.beacon.capture.Manageable;
import com.dolfdijkstra.beacon.capture.RequestEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileCapturedRequestPersister<T> implements CapturedRequestListener<T>, Manageable {
    static final Log log = LogFactory.getLog(FileCapturedRequestPersister.class);

    private final RequestEncoder<T, String> encoder;

    private File file;

    private PrintWriter out;

    private boolean flush = false;

    /**
     * @param file
     */
    public FileCapturedRequestPersister(final File file, boolean flush, RequestEncoder<T, String> encoder) {
        super();

        this.file = file;
        this.flush = flush;
        this.encoder = encoder;
        file.getParentFile().mkdirs();
    }

    public void capturedRequestReceived(final T capturedRequest) {

        try {
            out.println(encoder.encode(capturedRequest));
        } catch (final IOException e) {
            log.error(e + ". Switching off output for now.");
            out = new PrintWriter(new NullWriter());
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void start() {
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"), flush);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void stop() {
        IOUtils.closeQuietly(out);
    }

    private static class NullWriter extends Writer {

        @Override
        public void close() throws IOException {
            // nothing

        }

        @Override
        public void flush() throws IOException {
            // nothing

        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            // nothing

        }

    }
}
