package com.dolfdijkstra.beacon.capture.full;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dolfdijkstra.beacon.capture.listener.CapturedRequestListener;

public class LoggingCapturedRequestPersister extends
        AbstractTextCapturedRequestPersister implements
        CapturedRequestListener<FullCapturedRequest> {
    private static final Log log = LogFactory
            .getLog(LoggingCapturedRequestPersister.class);

    public void capturedRequestReceived(FullCapturedRequest capturedRequest) {
        log.info(serialize(capturedRequest));
    }

}
