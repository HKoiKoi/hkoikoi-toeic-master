package com.hkoikoi.toeicMaster.domain.category.dto;

import java.util.ArrayList;
import java.util.List;

import com.hkoikoi.toeicMaster.domain.category.entity.Category;

public record CategoryTreeResponse(

	Long categoryId,
	String name,
	int depth,
	int displayOrder,
	List<CategoryTreeResponse> children
) {

	public static CategoryTreeResponse from(Category category) {
		return new CategoryTreeResponse(
			category.getId(),
			category.getName(),
			category.getDepth(),
			category.getDisplayOrder(),
			new ArrayList<>()
		);
	}
}
