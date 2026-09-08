package com.hkoikoi.toeicMaster.global.response;

public record FieldErrorDetail(

	String field,
	String message
) {

	public static FieldErrorDetail of(String field, String message) {
		return new FieldErrorDetail(field, message);
	}
}
