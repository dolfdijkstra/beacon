package com.dolfdijkstra.beacon.sensor;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiVariantTestingBeaconServlet extends HttpServlet {
    final Log log = LogFactory.getLog(this.getClass());
    /**
     * 
     */
    private static final long serialVersionUID = 5555204138074455219L;

    private MultiVariantTestManager mvt;

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        try {
            String id = request.getParameter("id");
            log.debug("id="+id);
            if (id != null) {
                mvt.hit(id);
            }
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
        mvt = (MultiVariantTestManager) config.getServletContext().getAttribute("MVT");
        if (mvt == null) {
            mvt = new MultiVariantTestManager();
            config.getServletContext().setAttribute("MVT", mvt);
        }
    }
}