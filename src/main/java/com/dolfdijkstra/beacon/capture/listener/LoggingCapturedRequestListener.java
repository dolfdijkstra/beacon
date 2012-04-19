package com.dolfdijkstra.beacon.capture.listener;

import com.dolfdijkstra.beacon.capture.RequestEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingCapturedRequestListener<T> implements CapturedRequestListener<T> {
    private static final Log log = LogFactory.getLog(LoggingCapturedRequestListener.class);

    private final RequestEncoder<T, String> encoder;

    /**
     * @param encoder
     */
    public LoggingCapturedRequestListener(RequestEncoder<T, String> encoder) {
        super();
        this.encoder = encoder;
    }

    public void capturedRequestReceived(final T capturedRequest) {
        try {
            log.info(encoder.encode(capturedRequest));
        } catch (Exception e) {
            log.error(e);
        }
    }

}
