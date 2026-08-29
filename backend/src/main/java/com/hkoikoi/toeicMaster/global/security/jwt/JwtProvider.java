package com.hkoikoi.toeicMaster.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hkoikoi.toeicMaster.global.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private final String issuer;
	private final SecretKey secretKey;
	private final long accessTokenValidityInMilliseconds;
	private final long refreshTokenValidityInMilliseconds;

	public JwtProvider(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.issuer}") String issuer,
		@Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
		@Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
	) {

		this.issuer = issuer;
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
		this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
	}

	/**
	 * Access Token 생성
	 * 회원 ID(subject)와 권한(role) 정보를 포함합니다.
	 */
	public String createAccessToken(Long memberId, String role) {
		return createToken(memberId.toString(), role, accessTokenValidityInMilliseconds);
	}

	/**
	 * Refresh Token 생성
	 * Access Token 재발급 용도이므로 회원 ID(subject)만 포함
	 */
	public String createRefreshToken(Long memberId) {
		return createToken(memberId.toString(), null, refreshTokenValidityInMilliseconds);
	}

	public boolean validateToken(String token) {

		try {

			Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);

			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.warn("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.warn("JWT 토큰이 잘못되었습니다.");
		}

		return false;
	}

	/**
	 * 토큰에서 회원 ID(Subject) 추출
	 */
	public Long getMemberIdFromToken(String token) {
		return Long.valueOf(parseClaims(token).getSubject());
	}

	/**
	 * 토큰에서 권한 추출
	 */
	public String getRoleFromToken(String token) {
		return parseClaims(token).get(SecurityConstants.ROLE_CLAIM, String.class);
	}

	private String createToken(String subject, String role, long validityInMilliseconds) {

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.issuer(issuer)
			.subject(subject)
			.claim(SecurityConstants.ROLE_CLAIM, role)
			.issuedAt(now)
			.expiration(validity)
			.signWith(secretKey)
			.compact();
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parser()
				.verifyWith(this.secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
