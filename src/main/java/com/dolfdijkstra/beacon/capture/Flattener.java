package com.dolfdijkstra.beacon.capture;

/**
 * <p>Flattens a CapturedRequest into a NameValuePair array.</p>
 * <p>Usefull when a CapturedRequest needs to be processed further in a flattened form, like a database row 
 * or a file</p>
 * 
 * @author Dolf.Dijkstra
 * @since Jul 24, 2008
 * @param <E>
 */

public interface Flattener<E> {
    /**
     * <p>transforms a request into a NameValuePair array</p> 
     * @param request
     * @return
     */
    NameValuePair[] flatten(E request);

}
