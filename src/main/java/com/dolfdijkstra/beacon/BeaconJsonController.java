package com.dolfdijkstra.beacon;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolfdijkstra.beacon.capture.Manageable;
import com.dolfdijkstra.beacon.capture.RequestCapturer;
import com.dolfdijkstra.beacon.capture.listener.CapturedRequestListener;
import com.dolfdijkstra.beacon.capture.view.View;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeaconJsonController<T> implements Manageable {

    private final RequestCapturer<T> postCapturer;
    private final CapturedRequestListener<T> postListener;
    private final View view;

    private Log log = LogFactory.getLog(getClass());

    private boolean stop = false;

    /**
     * @param postCapturer
     * @param postListener
     * @param view
     */
    public BeaconJsonController(RequestCapturer<T> postCapturer,
            CapturedRequestListener<T> postListener, View view) {
        super();
        this.postCapturer = postCapturer;
        this.postListener = postListener;
        this.view = view;
    }

    public void handlePost(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!stop) {
                T req = postCapturer.capture(request);

                if (req != null)
                    postListener.capturedRequestReceived(req);
            }
        } catch (Throwable e) {
            log.error("Exception reading input stream", e);
        }

        try {
            view.render(request, response);
        } catch (IOException e) {
            log.debug(e.getMessage());
        } catch (ServletException e) {
            log.debug(e.getMessage());
        }
    }

    public void handleGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("<h1>beacon</h1>");

    }

    @Override
    public void start() {
        stop = false;

    }

    @Override
    public void stop() {
        stop = true;

    }

}
