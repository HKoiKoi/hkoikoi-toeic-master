package com.hkoikoi.toeicMaster.domain.category.dto;

import com.hkoikoi.toeicMaster.domain.category.entity.Category;

public record CategoryCreateResponse(

	Long categoryId
) {

	public static CategoryCreateResponse from(Category category) {
		return new CategoryCreateResponse(category.getId());
	}
}
