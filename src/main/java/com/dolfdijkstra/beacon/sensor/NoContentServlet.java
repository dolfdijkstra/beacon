package com.dolfdijkstra.beacon.sensor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolfdijkstra.beacon.capture.view.NoContentView;
import com.dolfdijkstra.beacon.capture.view.View;

public class NoContentServlet extends HttpServlet {

    
    View view = new NoContentView();
    private final Object data=null;
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
        view.render(request, response, data);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#getLastModified(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected long getLastModified(final HttpServletRequest req) {
        return -1;
    }

}