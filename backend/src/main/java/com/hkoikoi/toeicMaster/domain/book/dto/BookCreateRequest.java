package com.hkoikoi.toeicMaster.domain.book.dto;

import com.hkoikoi.toeicMaster.domain.book.enums.BookType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateRequest(

	@NotBlank(message = "교재명은 필수입니다.")
	String title,

	String publisher,

	@NotNull(message = "교재 유형은 필수입니다.")
	BookType bookType
) {
}
