package com.dolfdijkstra.beacon;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolfdijkstra.beacon.capture.Manageable;
import com.dolfdijkstra.beacon.capture.RequestEncoder;
import com.dolfdijkstra.beacon.capture.body.BodyCapturedRequest;
import com.dolfdijkstra.beacon.capture.body.BodyRequestCapturer;
import com.dolfdijkstra.beacon.capture.body.JsonEncoder;
import com.dolfdijkstra.beacon.capture.listener.FileCapturedRequestPersister;
import com.dolfdijkstra.beacon.capture.listener.RequestCapturerConsumer;
import com.dolfdijkstra.beacon.capture.view.NoContentView;
import com.dolfdijkstra.beacon.capture.view.View;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.dolfdijkstra.beacon.Utils.COOKIE_NAME;
import static com.dolfdijkstra.beacon.Utils.VERSION;
import static com.dolfdijkstra.beacon.Utils.getUid;

@SuppressWarnings("serial")
public class BeaconServlet extends HttpServlet {
    private static Log log = LogFactory.getLog(BeaconServlet.class.getName());

    private static final int TWO_YEARS_IN_SECONDS = 2 * 365 * 24 * 60 * 60;

    private BeaconJsonController<BodyCapturedRequest> controller;
    private List<Manageable> manageables = new LinkedList<Manageable>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendCookie(request, response);
        controller.handleGet(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        sendCookie(request, response);
        controller.handlePost(request, response);

    }

    private String sendCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uid = getUid(request);
        if (uid == null) {
            UUID uuid = UUID.randomUUID();
            uid = uuid.toString();
            Cookie cookie = new Cookie(COOKIE_NAME, uid + ":" + VERSION);
            cookie.setMaxAge(TWO_YEARS_IN_SECONDS);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return uid;

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sendAllow(resp);
    }

    /**
     * @param response
     * @throws IOException
     */
    private void sendAllow(HttpServletResponse response) throws IOException {
        response.setHeader("Allow", "GET, POST");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sendAllow(resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sendAllow(resp);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sendAllow(resp);

    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        sendAllow(resp);

    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }

    @Override
    public void destroy() {

        for (Manageable m : manageables) {
            try {
                m.stop();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        super.destroy();
    }

    @Override
    public void init() throws ServletException {

        View view = new NoContentView();

        RequestEncoder<BodyCapturedRequest, String> encoder = new JsonEncoder();
        File dir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        File file = new java.io.File(dir,"logs/beacon-json.log");

        FileCapturedRequestPersister<BodyCapturedRequest> delegate = new FileCapturedRequestPersister<BodyCapturedRequest>(
                file, true, encoder);
        delegate.start();

        RequestCapturerConsumer<BodyCapturedRequest> consumer = new RequestCapturerConsumer<BodyCapturedRequest>(
                delegate);
        consumer.start();

        BodyRequestCapturer postCapturer = new BodyRequestCapturer();

        controller = new BeaconJsonController<BodyCapturedRequest>(postCapturer, consumer, view);
        controller.start();
        manageables.add(controller);
        manageables.add(consumer);
        manageables.add(delegate);

    }

}
