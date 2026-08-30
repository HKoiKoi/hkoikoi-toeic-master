package com.hkoikoi.toeicMaster.global.security.oauth2;

import static java.util.Objects.*;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hkoikoi.toeicMaster.global.security.SecurityConstants;
import com.hkoikoi.toeicMaster.global.security.jwt.JwtProvider;
import com.hkoikoi.toeicMaster.global.security.jwt.RefreshTokenRepository;
import com.hkoikoi.toeicMaster.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;

	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${app.oauth2.frontend-redirect-url}")
	private String frontendRedirectUrl;

	@Value("${jwt.refresh-token-validity-in-seconds}")
	private long refreshTokenValidityInSeconds;

	@Override
	public void onAuthenticationSuccess(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull Authentication authentication
	) throws IOException {

		CustomOAuth2User oAuth2User = (CustomOAuth2User)requireNonNull(
			authentication.getPrincipal(), "인증된 Principal 객체가 존재하지 않습니다."
		);

		Long memberId = oAuth2User.memberId();
		String role = oAuth2User.role();

		String accessToken = jwtProvider.createAccessToken(memberId, role);
		String refreshToken = jwtProvider.createRefreshToken(memberId);

		log.info("OAuth2 로그인 성공. 발급된 Member ID: {}, Role: {}", memberId, role);

		refreshTokenRepository.save(memberId, refreshToken);

		CookieUtil.addCookie(
			response,
			SecurityConstants.REFRESH_TOKEN_COOKIE_NAME,
			refreshToken,
			refreshTokenValidityInSeconds
		);

		String targetUrl = UriComponentsBuilder.fromUriString(frontendRedirectUrl)
			.queryParam(SecurityConstants.QUERY_PARAM_ACCESS_TOKEN, accessToken)
			.build().toUriString();

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
