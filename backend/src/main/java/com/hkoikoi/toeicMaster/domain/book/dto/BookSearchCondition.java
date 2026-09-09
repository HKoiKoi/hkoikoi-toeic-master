package com.hkoikoi.toeicMaster.domain.book.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.hkoikoi.toeicMaster.domain.book.enums.BookType;

public record BookSearchCondition(

	String title,

	BookType bookType,

	Boolean isActive,

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate,

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate,

	Integer page,

	Integer pageSize
) {

	public BookSearchCondition {

		if (page == null || page < 1) {
			page = 1;
		}

		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
	}
}
