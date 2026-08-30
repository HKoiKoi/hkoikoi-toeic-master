package com.hkoikoi.toeicMaster.domain.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.auth.dto.TokenRefreshResponse;
import com.hkoikoi.toeicMaster.domain.auth.service.AuthService;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;
import com.hkoikoi.toeicMaster.global.security.SecurityConstants;
import com.hkoikoi.toeicMaster.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/refresh")
	public ApiResponse<TokenRefreshResponse> refresh(
		@CookieValue(value = SecurityConstants.REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken
	) {

		if (refreshToken == null || refreshToken.isBlank()) {
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		return ApiResponse.success(authService.reissueToken(refreshToken));
	}

	@PostMapping("/logout")
	public ApiResponse<Void> logout(
		@AuthenticationPrincipal Long currentMemberId,
		HttpServletRequest request,
		HttpServletResponse response
	) {

		authService.logout(currentMemberId);

		CookieUtil.deleteCookie(request, response, SecurityConstants.REFRESH_TOKEN_COOKIE_NAME);

		return ApiResponse.success();
	}
}
