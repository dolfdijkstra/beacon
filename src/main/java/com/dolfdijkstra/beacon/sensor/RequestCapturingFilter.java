package com.dolfdijkstra.beacon.sensor;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolfdijkstra.beacon.capture.Manageable;
import com.dolfdijkstra.beacon.capture.RequestCapturer;
import com.dolfdijkstra.beacon.capture.RequestEncoder;
import com.dolfdijkstra.beacon.capture.listener.CapturedRequestListener;
import com.dolfdijkstra.beacon.capture.listener.FileCapturedRequestPersister;
import com.dolfdijkstra.beacon.capture.listener.RequestCapturerConsumer;
import com.dolfdijkstra.beacon.capture.minimal.MinimalCapturedRequest;
import com.dolfdijkstra.beacon.capture.minimal.MinimalCapturedRequestToMulitLineStringEncoder;
import com.dolfdijkstra.beacon.capture.minimal.MinimalRequestCapturer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestCapturingFilter implements Filter {
    final Log log = LogFactory.getLog(this.getClass());

    private RequestCapturer<MinimalCapturedRequest> capturer;

    private CapturedRequestListener<MinimalCapturedRequest> listener;

    public void destroy() {
        if (listener instanceof Manageable) {
            ((Manageable) listener).stop();
        }
        if (capturer instanceof Manageable) {
            ((Manageable) capturer).stop();
        }
        capturer = null;
        listener = null;
    }

    public final void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            doHttpFilter((HttpServletRequest) request,
                    (HttpServletResponse) response, chain);
        } else {
            chain.doFilter(request, response);
        }

    }

    protected void doHttpFilter(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        try {
            long t = log.isDebugEnabled() ? System.nanoTime() : 0;
            final MinimalCapturedRequest capturedRequest = capturer
                    .capture(request);
            listener.capturedRequestReceived(capturedRequest);
            if (t > 0) {
                log
                        .debug("capuring and notifying took: "
                                + Long.toString((System.nanoTime() - t) / 1000)
                                + "us.");
            }
        } catch (Throwable e) {
            log.warn(e.getMessage(), e);
        } finally {
            chain.doFilter(request, response);
        }

    }

    public void init(final FilterConfig filterConfig) throws ServletException {
        File file = new File(filterConfig.getInitParameter("file"));
        RequestEncoder<MinimalCapturedRequest,String> encoder=new MinimalCapturedRequestToMulitLineStringEncoder();
        listener = new RequestCapturerConsumer<MinimalCapturedRequest>(
                new FileCapturedRequestPersister<MinimalCapturedRequest>(file, true, encoder));
        if (listener instanceof Manageable) {
            ((Manageable) listener).start();
        }
        capturer = new MinimalRequestCapturer();

        if (capturer instanceof Manageable) {
            ((Manageable) capturer).start();
        }

    }

}
