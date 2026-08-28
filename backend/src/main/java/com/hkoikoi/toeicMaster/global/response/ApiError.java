package com.hkoikoi.toeicMaster.global.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiError(

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime timestamp,

	String code,

	String message,

	List<FieldErrorDetail> details
) {

	public static ApiError of(String code, String message) {
		return new ApiError(LocalDateTime.now(), code, message, null);
	}

	public static ApiError of(String code, String message, List<FieldErrorDetail> details) {
		return new ApiError(LocalDateTime.now(), code, message, details);
	}
}
