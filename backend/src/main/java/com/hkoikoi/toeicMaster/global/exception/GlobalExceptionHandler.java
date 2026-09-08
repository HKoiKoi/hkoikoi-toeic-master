package com.hkoikoi.toeicMaster.global.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hkoikoi.toeicMaster.global.response.ApiResponse;
import com.hkoikoi.toeicMaster.global.response.ErrorResponse;
import com.hkoikoi.toeicMaster.global.response.FieldErrorDetail;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 1. 커스텀 비즈니스 예외 처리
	 * HTTP Status: ErrorCode에 정의된 Status
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {

		log.warn("[BusinessException] code: {}, message: {}", ex.getErrorCodeString(), ex.getMessage());

		return buildErrorResponse(ex.getErrorCode(), ex.getMessage());
	}

	/**
	 * 2. @Valid DTO 검증 실패 예외 처리 (RequestBody, ModelAttribute)
	 * HTTP Status: 400 BAD_REQUEST
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		log.warn("[MethodArgumentNotValidException] message: {}", ex.getMessage());

		List<FieldErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
			.map(error -> FieldErrorDetail.of(error.getField(), error.getDefaultMessage()))
			.toList();

		return buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE, ErrorCode.INVALID_INPUT_VALUE.getMessage(), details);
	}

	/**
	 * 3. @Validated 파라미터 검증 실패 예외 처리 (RequestParam, PathVariable)
	 * HTTP Status: 400 BAD_REQUEST
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {

		log.warn("[ConstraintViolationException] message: {}", ex.getMessage());

		ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

		List<FieldErrorDetail> details = ex.getConstraintViolations().stream()
			.map(violation -> FieldErrorDetail.of(violation.getPropertyPath().toString(), violation.getMessage()))
			.toList();

		return buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE, ErrorCode.INVALID_INPUT_VALUE.getMessage(), details);
	}

	/**
	 * 4. JSON 파싱 실패 예외 처리
	 * HTTP Status: 400 BAD_REQUEST
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

		log.warn("[HttpMessageNotReadableException] message: {}", ex.getMessage());

		return buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
	}

	/**
	 * 5. 지원하지 않는 HTTP Method 호출 예외 처리
	 * HTTP Status: 405 METHOD_NOT_ALLOWED
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex
	) {

		log.warn("HttpRequestMethodNotSupportedException: {}", ex.getMessage());

		return buildErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
	}

	/**
	 * 6. 그 외 서버 내부에서 발생한 모든 예외 처리 (Catch-All)
	 * HTTP Status: 500 INTERNAL_SERVER_ERROR
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {

		log.error("Unhandled Exception: ", ex);

		return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ApiResponse<Void>> buildErrorResponse(ErrorCode errorCode) {
		return buildErrorResponse(errorCode, errorCode.getMessage(), null);
	}

	private ResponseEntity<ApiResponse<Void>> buildErrorResponse(ErrorCode errorCode, String customMessage) {
		return buildErrorResponse(errorCode, customMessage, null);
	}

	private ResponseEntity<ApiResponse<Void>> buildErrorResponse(
		ErrorCode errorCode,
		String customMessage,
		List<FieldErrorDetail> details
	) {
		
		ErrorResponse errorResponse = (details == null || details.isEmpty())
			? ErrorResponse.of(errorCode.getCode(), customMessage)
			: ErrorResponse.of(errorCode.getCode(), customMessage, details);

		return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.error(errorResponse));
	}
}
