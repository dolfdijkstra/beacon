package com.dolfdijkstra.beacon.capture.full;

import com.dolfdijkstra.beacon.capture.listener.CapturedRequestListener;

public class StdOutCapturedRequestPersister extends
        AbstractTextCapturedRequestPersister implements
        CapturedRequestListener<FullCapturedRequest> {

    public void capturedRequestReceived(FullCapturedRequest capturedRequest) {
        System.out.println(serialize(capturedRequest));

    }

}
