package com.hkoikoi.toeicMaster.global.security.oauth2;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Value("${app.oauth2.frontend-login-url}")
	private String frontendLoginUrl;

	@Override
	public void onAuthenticationFailure(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		AuthenticationException exception
	) throws IOException {

		log.error("OAuth2 로그인 실패: {}", exception.getMessage());

		String targetUrl = UriComponentsBuilder.fromUriString(frontendLoginUrl)
			.queryParam("error", "login_failed")
			.build().toUriString();

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
