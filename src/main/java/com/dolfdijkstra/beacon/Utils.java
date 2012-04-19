package com.dolfdijkstra.beacon;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Utils {
	public static final String COOKIE_NAME = "uid";
	public static final String VERSION = "1";
	public static String getUid(HttpServletRequest request) {
		String uid = null;
		Cookie[] cookies = request.getCookies();
		// String remote=request.getRemoteAddr() +":" + request.getRemotePort();
		// request.getHeader("User-Agent");
		// request.getHeader("Referer");

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (uid == null && COOKIE_NAME.equals(cookie.getName())) {
					String v = cookie.getValue();
					if (v != null) {
						int t = v.indexOf(':');
						if (t > 5) {
							String version = v.substring(t + 1);
							if (VERSION.equals(version)) {
								uid = v.substring(0, t);
							}

						}
					}

				}
			}
		}
		return uid;
	}

}
