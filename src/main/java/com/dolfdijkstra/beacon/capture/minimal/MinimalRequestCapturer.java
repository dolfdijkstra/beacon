package com.dolfdijkstra.beacon.capture.minimal;

import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.dolfdijkstra.beacon.capture.CaptureException;
import com.dolfdijkstra.beacon.capture.RequestCapturer;

import org.apache.commons.io.IOUtils;

public class MinimalRequestCapturer implements RequestCapturer<MinimalCapturedRequest> {

    @SuppressWarnings("unchecked")
    public MinimalCapturedRequest capture(final HttpServletRequest request) throws CaptureException {

        final MinimalCapturedRequest cap = new MinimalCapturedRequest();
        cap.setUUID(UUID.randomUUID());
        cap.setTimestamp(System.currentTimeMillis());

        cap.setMethod(request.getMethod());
        cap.setProtocol(request.getProtocol());
        cap.setQueryString(request.getQueryString());
        cap.setRemoteAddr(request.getRemoteAddr());
        cap.setRequestedSessionIdValid(request.isRequestedSessionIdValid());
        cap.setRequestedSessionId(request.getRequestedSessionId());
        cap.setRequestURI(request.getRequestURI());
        cap.setRequestURL(request.getRequestURL() == null ? null : request.getRequestURL().toString());
        cap.setSecure(request.isSecure());
        cap.setServerName(request.getServerName());
        cap.setServerPort(request.getServerPort());

        for (final Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
            final String name = e.nextElement();
            for (final Enumeration<String> v = request.getHeaders(name); v.hasMoreElements();) {
                final String value = v.nextElement();
                cap.addHeader(name.toLowerCase(), value);
            }
        }

        return cap;
    }

    public void start() {

    }

    public void stop() {

    }

}
