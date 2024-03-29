package com.dolfdijkstra.beacon.sensor;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolfdijkstra.beacon.capture.RequestCapturer;
import com.dolfdijkstra.beacon.capture.full.FullCapturedRequest;
import com.dolfdijkstra.beacon.capture.full.FullRequestCapturer;
import com.dolfdijkstra.beacon.capture.full.LoggingCapturedRequestPersister;
import com.dolfdijkstra.beacon.capture.listener.CapturedRequestListener;

public class BeaconServlet extends HttpServlet {
    private RequestCapturer<FullCapturedRequest> capturer;

    private CapturedRequestListener<FullCapturedRequest> listener;

    /**
     * 
     */
    private static final long serialVersionUID = 5555204138074455219L;

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        try {
            FullCapturedRequest cap = capturer.capture(request);
            listener.capturedRequestReceived(cap);

        } catch (Throwable t) {
            this.log(t.getMessage(), t);
        }
        response.addHeader("Cache-Control", "no-store, no-cache, private");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#getLastModified(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected long getLastModified(final HttpServletRequest req) {
        return -1;
    }

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        FullRequestCapturer c = new FullRequestCapturer();
        listener = new LoggingCapturedRequestPersister();
        this.capturer = c;

    }
}