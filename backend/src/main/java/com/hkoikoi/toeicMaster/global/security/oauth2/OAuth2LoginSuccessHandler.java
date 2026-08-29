package com.hkoikoi.toeicMaster.global.security.oauth2;

import static java.util.Objects.*;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hkoikoi.toeicMaster.global.security.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;

	@Value("${app.oauth2.frontend-redirect-url}")
	private String frontendRedirectUrl;

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

		// TODO: 발급된 Refresh Token을 Redis에 저장하여 추후 검증 및 로그아웃에 사용하는 로직 추가

		String targetUrl = UriComponentsBuilder.fromUriString(frontendRedirectUrl)
			.queryParam("accessToken", accessToken)
			.build().toUriString();

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
