package com.dolfdijkstra.beacon.capture.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ZeroPixelImageView implements View {
    private final byte[] image = new byte[] { (byte) 71, (byte) 73, (byte) 70, (byte) 56, (byte) 57, (byte) 97,
            (byte) 1, (byte) 0, (byte) 1, (byte) 0, (byte) -128, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
            (byte) -1, (byte) -1, (byte) -1, (byte) 33, (byte) -7, (byte) 4, (byte) 1, (byte) 0, (byte) 0, (byte) 1,
            (byte) 0, (byte) 44, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 1, (byte) 0,
            (byte) 64, (byte) 2, (byte) 2, (byte) 76, (byte) 1, (byte) 0, (byte) 59 };

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Object... data) throws IOException,
            ServletException {
        response.addHeader("Cache-Control", "private, no-cache, no-cache=Set-Cookie, proxy-revalidate");

        // response.setStatus(HttpServletResponse.SC_NO_CONTENT); //gives an
        // error on the image
        response.setContentType("image/gif");
        response.getOutputStream().write(image);
        response.flushBuffer();

    }

}
