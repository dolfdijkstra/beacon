package com.dolfdijkstra.beacon.capture;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Factory for CapturedRequests
 * 
 * @author Dolf.Dijkstra
 * @since Feb 21, 2008
 * @param <E>
 */
public interface RequestCapturer<E> {
    E capture(HttpServletRequest request) throws CaptureException;

}
