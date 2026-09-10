package com.hkoikoi.toeicMaster.domain.book.dto;

import com.hkoikoi.toeicMaster.domain.book.entity.Book;

public record BookCreateResponse(
	
	Long bookId
) {

	public static BookCreateResponse from(Book book) {
		return new BookCreateResponse(book.getId());
	}
}
