package com.hkoikoi.toeicMaster.domain.book.service;

import org.springframework.stereotype.Service;

import com.hkoikoi.toeicMaster.domain.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
}
