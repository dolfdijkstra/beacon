/**
 * 
 */
package com.dolfdijkstra.beacon.capture.minimal;

import com.dolfdijkstra.beacon.capture.NameValuePair;
import com.dolfdijkstra.beacon.capture.RequestEncoder;

final class MinimalCapturedRequestToStringEncoder implements
        RequestEncoder<MinimalCapturedRequest, String> {

    public String encode(final MinimalCapturedRequest capturedRequest) {
        final StringBuilder b = new StringBuilder();
        b.append(capturedRequest.getProtocol());
        b.append(' ');

        b.append(capturedRequest.getRequestURL());
        if (capturedRequest.getQueryString() != null) {
            b.append('?');
            b.append(capturedRequest.getQueryString());
        }
        for (final NameValuePair header : capturedRequest.getHeaders()) {
            b.append(' ');
            b.append(header.toString());
        }

        return b.toString();

    }
}