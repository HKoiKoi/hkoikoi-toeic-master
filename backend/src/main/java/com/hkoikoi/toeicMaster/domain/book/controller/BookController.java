package com.hkoikoi.toeicMaster.domain.book.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateRequest;
import com.hkoikoi.toeicMaster.domain.book.dto.BookCreateResponse;
import com.hkoikoi.toeicMaster.domain.book.service.BookService;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

	private final BookService bookService;

	@PostMapping
	public ApiResponse<BookCreateResponse> createBook(@Valid @RequestBody BookCreateRequest request) {

		return ApiResponse.success(bookService.createBook(request));
	}
}
