package com.hkoikoi.toeicMaster.global.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstants {

	// JWT Constants
	public static final String ROLE_CLAIM = "role";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	// OAuth2 & Auth Constants
	public static final String ROLE_PREFIX = "ROLE_";

	// Query Parameters
	public static final String QUERY_PARAM_ACCESS_TOKEN = "accessToken";
	public static final String QUERY_PARAM_ERROR = "error";
	public static final String ERROR_LOGIN_FAILED = "login_failed";
}
