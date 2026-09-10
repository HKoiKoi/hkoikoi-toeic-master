package com.hkoikoi.toeicMaster.domain.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.category.dto.CategoryTreeResponse;
import com.hkoikoi.toeicMaster.domain.category.service.CategoryService;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/{bookId}/categories")
	public ApiResponse<List<CategoryTreeResponse>> getCategoryTree(@PathVariable Long bookId) {
		return ApiResponse.success(categoryService.getCategoryTree(bookId));
	}
}
