package com.hkoikoi.toeicMaster.domain.category.service;

import org.springframework.stereotype.Service;

import com.hkoikoi.toeicMaster.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;
}
