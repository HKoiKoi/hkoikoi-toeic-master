package com.hkoikoi.toeicMaster.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record FieldErrorDetail(

	String field,
	String message
) {
}
