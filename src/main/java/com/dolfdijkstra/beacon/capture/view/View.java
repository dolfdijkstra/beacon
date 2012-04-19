package com.dolfdijkstra.beacon.capture.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {

    void render(HttpServletRequest request, HttpServletResponse response, Object... data) throws IOException,
            ServletException;
}
