package com.hkoikoi.toeicMaster.global.security.jwt;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.hkoikoi.toeicMaster.global.security.SecurityConstants;

@Repository
public class RefreshTokenRepository {

	private final StringRedisTemplate redisTemplate;

	private final long refreshTokenValidityInMilliseconds;

	public RefreshTokenRepository(
		StringRedisTemplate redisTemplate,
		@Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
	) {
		this.redisTemplate = redisTemplate;
		this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000L;
	}

	/**
	 * Refresh Token 저장
	 */
	public void save(Long memberId, String refreshToken) {

		String key = SecurityConstants.REDIS_KEY_REFRESH_TOKEN + memberId;

		redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(refreshTokenValidityInMilliseconds));
	}

	/**
	 * Member ID로 저장된 Refresh Token 조회
	 */
	public String findByMemberId(Long memberId) {

		String key = SecurityConstants.REDIS_KEY_REFRESH_TOKEN + memberId;

		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * Member ID로 저장된 Refresh Token 삭제
	 */
	public void deleteByMemberId(Long memberId) {

		String key = SecurityConstants.REDIS_KEY_REFRESH_TOKEN + memberId;

		redisTemplate.delete(key);
	}
}
