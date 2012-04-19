/**
 * 
 */
package com.dolfdijkstra.beacon.capture.minimal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.dolfdijkstra.beacon.capture.NameValuePair;
import com.dolfdijkstra.beacon.capture.RequestEncoder;

public final class MinimalCapturedRequestToMulitLineStringEncoder implements
        RequestEncoder<MinimalCapturedRequest, String> {
    public static final String NEW_LINE = System.getProperty("line.separator");

    private final Set<String> HEADERS = new HashSet<String>();
    {
        HEADERS.addAll(Arrays.asList(new String[] { "host", "keep-alive",
                "connection" }));
    }

    public String encode(final MinimalCapturedRequest capturedRequest) {
        final StringBuilder b = new StringBuilder();
        b.append(capturedRequest.getUUID()).append(" ").append(
                capturedRequest.getTimestamp());
        b.append(MinimalCapturedRequestToMulitLineStringEncoder.NEW_LINE);
        b.append(capturedRequest.getMethod());
        b.append(' ');
        b.append(capturedRequest.getRequestURL());
        if (capturedRequest.getQueryString() != null) {
            b.append('?');
            b.append(capturedRequest.getQueryString());
        }
        b.append(' ');
        b.append(capturedRequest.getProtocol());
        b.append(MinimalCapturedRequestToMulitLineStringEncoder.NEW_LINE);
        for (final NameValuePair header : capturedRequest.getHeaders()) {
            if (!filter(header)) {
                b.append(header.getName().toLowerCase());
                b.append("=");
                b.append(header.getValue());
                b
                        .append(MinimalCapturedRequestToMulitLineStringEncoder.NEW_LINE);
            }
        }
        b.append(MinimalCapturedRequestToMulitLineStringEncoder.NEW_LINE);

        return b.toString();

    }

    /**
     * @param header
     * @return true if the header should not be put in the encoded message and true if header.getName() returns null
     */
    protected boolean filter(final NameValuePair header) {
        if (header.getName() == null) {
            return true;
        }
        return HEADERS.contains(header.getName().toLowerCase());
    }
}