package com.dolfdijkstra.beacon.capture.listener;

public interface CapturedRequestListener<E> {

    void capturedRequestReceived(E capturedRequest);
    
    
}
