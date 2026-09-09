package com.hkoikoi.toeicMaster.domain.book.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateRequest;
import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateResponse;
import com.hkoikoi.toeicMaster.domain.book.dto.BookPageResponse;
import com.hkoikoi.toeicMaster.domain.book.dto.BookSearchCondition;
import com.hkoikoi.toeicMaster.domain.book.dto.BookSearchResponse;
import com.hkoikoi.toeicMaster.domain.book.entity.Book;
import com.hkoikoi.toeicMaster.domain.book.repository.BookQueryRepository;
import com.hkoikoi.toeicMaster.domain.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

	private final BookRepository bookRepository;
	private final BookQueryRepository bookQueryRepository;

	public BookPageResponse searchBooks(BookSearchCondition condition) {

		long offset = (long)(condition.page() - 1) * condition.pageSize();

		List<BookSearchResponse> books = bookQueryRepository.searchBooks(condition, offset, condition.pageSize());

		Long totalCount = bookQueryRepository.countBooks(condition);

		return BookPageResponse.of(books, totalCount);
	}

	@Transactional
	public BookCreateResponse createBook(BookCreateRequest request) {

		Book book = Book.create(request.title(), request.publisher(), request.bookType());

		Book savedBook = bookRepository.save(book);

		return BookCreateResponse.from(savedBook);
	}
}
