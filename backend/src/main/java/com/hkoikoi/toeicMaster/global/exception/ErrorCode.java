package com.hkoikoi.toeicMaster.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

	// ----------------------------------------
	// 공통 (Common) 에러
	// ----------------------------------------
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "COMMON_001", "입력값이 올바르지 않습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_002", "지원하지 않는 HTTP Method 형식입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_003", "서버 내부 오류가 발생했습니다."),

	// ----------------------------------------
	// 사용자 (Member) 에러
	// ----------------------------------------
	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "MEMBER_001", "존재하지 않는 회원입니다."),
	CONFLICT_MEMBER_NICKNAME(HttpStatus.CONFLICT, "MEMBER_002", "이미 사용 중인 닉네임입니다."),

	// ----------------------------------------
	// 인증 (Auth) 에러
	// ----------------------------------------
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_001", "인증이 필요합니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_002", "접근 권한이 없습니다."),
	;

	HttpStatus status;
	String code;
	String message;
}
