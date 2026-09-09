package com.hkoikoi.toeicMaster.domain.book.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateRequest;
import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateResponse;
import com.hkoikoi.toeicMaster.domain.book.entity.Book;
import com.hkoikoi.toeicMaster.domain.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	@Transactional
	public BookCreateResponse createBook(BookCreateRequest request) {

		Book book = Book.create(request.title(), request.publisher(), request.bookType());

		Book savedBook = bookRepository.save(book);

		return BookCreateResponse.from(savedBook);
	}
}
