package com.hkoikoi.toeicMaster.domain.book.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateRequest;
import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateResponse;
import com.hkoikoi.toeicMaster.domain.book.dto.BookPageResponse;
import com.hkoikoi.toeicMaster.domain.book.dto.BookSearchCondition;
import com.hkoikoi.toeicMaster.domain.book.dto.BookUpdateRequest;
import com.hkoikoi.toeicMaster.domain.book.service.BookService;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

	private final BookService bookService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<BookPageResponse> searchBooks(
		@ModelAttribute BookSearchCondition condition
	) {
		return ApiResponse.success(bookService.searchBooks(condition));
	}

	@PostMapping
	public ApiResponse<BookCreateResponse> createBook(@Valid @RequestBody BookCreateRequest request) {

		return ApiResponse.success(bookService.createBook(request));
	}

	@PatchMapping("/{bookId}")
	public ApiResponse<Void> updateBook(
		@PathVariable Long bookId,
		@Valid @RequestBody BookUpdateRequest request
	) {

		bookService.updateBook(bookId, request);

		return ApiResponse.success();
	}
}
