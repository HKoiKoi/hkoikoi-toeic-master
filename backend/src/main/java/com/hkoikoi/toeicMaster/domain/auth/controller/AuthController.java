package com.hkoikoi.toeicMaster.domain.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.auth.dto.TokenRefreshRequest;
import com.hkoikoi.toeicMaster.domain.auth.dto.TokenRefreshResponse;
import com.hkoikoi.toeicMaster.domain.auth.service.AuthService;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/refresh")
	public ApiResponse<TokenRefreshResponse> refresh(
		@Valid @RequestBody TokenRefreshRequest request
	) {
		return ApiResponse.success(authService.reissueToken(request.refreshToken()));
	}
}
