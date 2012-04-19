package com.dolfdijkstra.beacon.capture.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoContentView implements View {

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Object... data) throws IOException,
            ServletException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.addHeader("Cache-Control", "private, no-cache, no-cache=Set-Cookie, proxy-revalidate");

    }

}
