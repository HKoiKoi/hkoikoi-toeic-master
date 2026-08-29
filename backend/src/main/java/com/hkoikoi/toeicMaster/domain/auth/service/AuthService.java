package com.hkoikoi.toeicMaster.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.auth.dto.TokenRefreshResponse;
import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.repository.MemberRepository;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;
import com.hkoikoi.toeicMaster.global.security.jwt.JwtProvider;
import com.hkoikoi.toeicMaster.global.security.jwt.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional(readOnly = true)
	public TokenRefreshResponse reissueToken(String refreshToken) {

		if (!jwtProvider.validateToken(refreshToken)) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		Long memberId = jwtProvider.getMemberIdFromToken(refreshToken);

		String storedRefreshToken = refreshTokenRepository.findByMemberId(memberId);
		if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

		String newAccessToken = jwtProvider.createAccessToken(member.getId(), member.getRole().toString());

		return TokenRefreshResponse.of(newAccessToken);
	}

	@Transactional
	public void logout(Long memberId) {
		refreshTokenRepository.deleteByMemberId(memberId);
	}
}
