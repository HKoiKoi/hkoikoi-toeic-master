package com.hkoikoi.toeicMaster.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(

	boolean result,

	T data,

	ErrorResponse error
) {

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(Boolean.TRUE, data, null);
	}

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>(Boolean.TRUE, null, null);
	}

	public static <T> ApiResponse<T> error(ErrorResponse error) {
		return new ApiResponse<>(Boolean.FALSE, null, error);
	}
}
