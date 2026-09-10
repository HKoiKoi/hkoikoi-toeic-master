package com.hkoikoi.toeicMaster.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCreateRequest(

	Long parentId,

	@NotBlank(message = "카테고리명은 필수입니다.")
	String name,

	@NotNull(message = "순서는 필수입니다.")
	Integer displayOrder
) {
}
