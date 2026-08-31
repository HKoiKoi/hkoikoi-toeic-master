package com.hkoikoi.toeicMaster.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {

	private static boolean cookieSecure;

	public static void addCookie(HttpServletResponse response, String name, String value, long maxAge) {

		ResponseCookie cookie = ResponseCookie.from(name, value)
			.path("/")
			.httpOnly(true)
			.secure(cookieSecure)
			.sameSite("Lax")
			.maxAge(maxAge)
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		ResponseCookie cookie = ResponseCookie.from(name, "")
			.path("/")
			.httpOnly(true)
			.secure(cookieSecure)
			.sameSite("Lax")
			.maxAge(0)
			.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	@Value("${app.oauth2.cookie-secure}")
	public void setCookieSecure(boolean cookieSecure) {
		CookieUtil.cookieSecure = cookieSecure;
	}
}
