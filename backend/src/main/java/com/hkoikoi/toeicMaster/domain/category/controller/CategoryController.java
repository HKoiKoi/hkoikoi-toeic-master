package com.hkoikoi.toeicMaster.domain.category.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.category.dto.CategoryCreateRequest;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryCreateResponse;
import com.hkoikoi.toeicMaster.domain.category.dto.CategoryTreeResponse;
import com.hkoikoi.toeicMaster.domain.category.service.CategoryService;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;

import jakarta.validation.Valid;
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

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{bookId}/categories")
	public ApiResponse<CategoryCreateResponse> createCategory(
		@PathVariable Long bookId,
		@Valid @RequestBody CategoryCreateRequest request
	) {
		return ApiResponse.success(categoryService.createCategory(bookId, request));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{bookId}/categories/{categoryId}")
	public ApiResponse<Void> deleteCategory(
		@PathVariable Long bookId,
		@PathVariable Long categoryId
	) {

		categoryService.deleteCategory(bookId, categoryId);

		return ApiResponse.success();
	}
}
