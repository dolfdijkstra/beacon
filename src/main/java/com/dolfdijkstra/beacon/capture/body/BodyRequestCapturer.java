package com.dolfdijkstra.beacon.capture.body;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import com.dolfdijkstra.beacon.capture.CaptureException;
import com.dolfdijkstra.beacon.capture.RequestCapturer;

import org.apache.commons.io.IOUtils;

public class BodyRequestCapturer implements RequestCapturer<BodyCapturedRequest> {
    
    private UUID uuid = UUID.randomUUID();
    private AtomicLong counter = new AtomicLong();

    @SuppressWarnings("unchecked")
    public BodyCapturedRequest capture(final HttpServletRequest request) throws CaptureException {

        String body = readBody(request);
        if (body != null && body.length() > 10) {
            final BodyCapturedRequest cap = new BodyCapturedRequest();
            cap.setBody(body);
            cap.setUUID(uuid);
            cap.setCount(counter.incrementAndGet());
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
        } else {
            return null;
        }

    }

    /**
     * @param request
     * @return
     * 
     */
    private String readBody(final HttpServletRequest request) {
        try {
            //request.getContentType();
            String enc = request.getCharacterEncoding();
            java.io.InputStream input = request.getInputStream();
            String x = IOUtils.toString(input, enc == null ? "UTF-8" : enc);
            IOUtils.closeQuietly(input);
            return x;
        } catch (IOException e) {
            // log.debug(e.getMessage());
            return null;
        }
    }

}
