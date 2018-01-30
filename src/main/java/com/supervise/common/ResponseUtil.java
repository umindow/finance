package com.supervise.common;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public class ResponseUtil {

	// 状态码响应
	public static void reponse(HttpServletResponse response, int statusCode, String message) throws IOException {
		response.setStatus(statusCode);
		response.getWriter().println(message);
		if (!response.isCommitted()) {
			response.getWriter().flush();
		}
	}

	// 重定向
	public static void redirect(HttpServletResponse response, String redirect) {
		response.setStatus(HttpServletResponse.SC_FOUND);
		response.setHeader(HttpHeaders.LOCATION, redirect);
	}

	// 添加cookie
	public static void addCookie(HttpServletResponse response, String name, String value, String domain, String path, int maxAge, boolean httpOnly) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(httpOnly);
		response.addCookie(cookie);
	}
}
