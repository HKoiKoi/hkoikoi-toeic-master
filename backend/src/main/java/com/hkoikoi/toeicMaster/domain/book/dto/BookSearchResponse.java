package com.hkoikoi.toeicMaster.domain.book.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hkoikoi.toeicMaster.domain.book.enums.BookType;

public record BookSearchResponse(

	Long bookId,

	String title,

	String publisher,

	BookType bookType,

	boolean isActive,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
}
