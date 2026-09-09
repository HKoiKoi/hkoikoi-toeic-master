package com.hkoikoi.toeicMaster.domain.book.dto;

import java.util.List;

public record BookPageResponse(

	List<BookSearchResponse> books,
	Long totalCount
) {

	public static BookPageResponse of(List<BookSearchResponse> books, Long totalCount) {
		return new BookPageResponse(books, totalCount);
	}
}
