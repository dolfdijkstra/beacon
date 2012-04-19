package com.dolfdijkstra.beacon;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dolfdijkstra.beacon.domain.Hit;

import static com.dolfdijkstra.beacon.Utils.*;

@SuppressWarnings("serial")
public class ExtBeaconServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ExtBeaconServlet.class.getName());

	private static final int TWO_YEARS_IN_SECONDS = 2 * 365 * 24 * 60 * 60;
	private static final TimeZone TZ = TimeZone.getTimeZone("GMT");

	private HitDao pm;
	
	private final byte[] image = new byte[] { (byte) 71, (byte) 73, (byte) 70,
			(byte) 56, (byte) 57, (byte) 97, (byte) 1, (byte) 0, (byte) 1,
			(byte) 0, (byte) -128, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
			(byte) 0, (byte) -1, (byte) -1, (byte) -1, (byte) 33, (byte) -7,
			(byte) 4, (byte) 1, (byte) 0, (byte) 0, (byte) 1, (byte) 0,
			(byte) 44, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1,
			(byte) 0, (byte) 1, (byte) 0, (byte) 64, (byte) 2, (byte) 2,
			(byte) 76, (byte) 1, (byte) 0, (byte) 59 };

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		persist(request, response);
		response.addHeader("Cache-Control",
				"private, no-cache, no-cache=Set-Cookie, proxy-revalidate");

		// response.setStatus(HttpServletResponse.SC_NO_CONTENT); //gives an error on the image
		response.setContentType("image/gif");
		response.getOutputStream().write(image);
		response.flushBuffer();

	}

	private void persist(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pl = request.getParameter("pl");
		if (pl == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		long start = System.nanoTime();
		String uid = getUid(request);
		if (uid == null) {
			UUID uuid = UUID.randomUUID();
			uid = uuid.toString();
			Cookie cookie = new Cookie(COOKIE_NAME, uid + ":" + VERSION);
			cookie.setMaxAge(TWO_YEARS_IN_SECONDS);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		String type = "load";
		if (request.getPathInfo() != null && request.getPathInfo().length() > 1) {
			String pi = request.getPathInfo().substring(1);
			if ("unload".equals(pi)) {
				type = pi;
			}
		}

		Hit hit = new Hit();
		hit.setTimeStamp(Calendar.getInstance(TZ).getTimeInMillis());
		hit.setOid(request.getParameter("oid"));
		hit.setUserAgent(request.getHeader("User-Agent"));
		hit.setType(type);

		if (uid != null) {
			hit.setUid(uid);
		}
		hit.setPayload(pl);
		long t0 = System.nanoTime();
		try {
			
			long t1 = System.nanoTime();
			long t2 = t1;
			try {
				pm.makePersistent(hit);
				t2 = System.nanoTime();
			} finally {
				pm.close();
			}
			long end = System.nanoTime();
			response.addHeader("persist-time", Long
					.toString((end - start) / 1000)
					+ " us, p"
					+ Long.toString((t0 - start) / 1000)
					+ " us, g"
					+ Long.toString((t1 - t0) / 1000)
					+ ",p"
					+ Long.toString((t2 - t1) / 1000)
					+ ", c"
					+ Long.toString((end - t2) / 1000));
		} catch (Throwable e) {
			log.log(Level.SEVERE,
					e.getMessage() + " thown after "
							+ Long.toString((System.nanoTime() - start) / 1000)
							+ " us", e);

		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "GET");
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "GET");
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "GET");
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "GET");
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "GET");
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "GET");
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

	@Override
	protected long getLastModified(HttpServletRequest req) {
		return -1;
	}

	@Override
	public void destroy() {
		log.entering(this.getClass().getName(), "destroy()");
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.entering(this.getClass().getName(), "init()");
	}

}
