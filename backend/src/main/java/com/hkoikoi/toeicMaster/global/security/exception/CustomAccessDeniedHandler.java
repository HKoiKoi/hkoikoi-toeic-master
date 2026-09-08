package com.hkoikoi.toeicMaster.global.security.exception;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		@NonNull AccessDeniedException accessDeniedException
	) throws IOException {

		log.warn("[AccessDeniedHandler] 권한이 부족한 접근입니다. URI: {}", request.getRequestURI());

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);    // 403
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getMessage());
		ApiResponse<Void> apiResponse = ApiResponse.error(errorResponse);

		objectMapper.writeValue(response.getWriter(), apiResponse);
	}
}
