package com.hkoikoi.toeicMaster.global.security.jwt;

import java.io.IOException;
import java.util.Collections;

import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hkoikoi.toeicMaster.global.security.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {

		String token = resolveToken(request);

		if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

			Long memberId = jwtProvider.getMemberIdFromToken(token);
			String role = jwtProvider.getRoleFromToken(token);

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(SecurityConstants.ROLE_PREFIX + role);

			Authentication authentication = new UsernamePasswordAuthenticationToken(
				memberId, null, Collections.singletonList(authority)
			);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.debug("Security Context에 Member ID: '{}' 인증 정보를 저장했습니다. URI: {}", memberId, request.getRequestURI());
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {

		String bearerToken = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			return bearerToken.substring(SecurityConstants.TOKEN_PREFIX.length());
		}

		return null;
	}
}
