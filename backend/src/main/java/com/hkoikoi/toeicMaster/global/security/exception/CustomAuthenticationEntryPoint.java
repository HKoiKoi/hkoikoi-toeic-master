package com.hkoikoi.toeicMaster.global.security.exception;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.hkoikoi.toeicMaster.global.exception.ErrorCode;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;
import com.hkoikoi.toeicMaster.global.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		@NonNull AuthenticationException exception
	) throws IOException {

		log.warn("[AuthenticationEntryPoint] 인증되지 않은 접근입니다. URI: {}", request.getRequestURI());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    // 401
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		ErrorResponse errorResponse =
			ErrorResponse.of(ErrorCode.UNAUTHORIZED.getCode(), ErrorCode.UNAUTHORIZED.getMessage());
		ApiResponse<Void> apiResponse = ApiResponse.error(errorResponse);

		objectMapper.writeValue(response.getWriter(), apiResponse);
	}
}
