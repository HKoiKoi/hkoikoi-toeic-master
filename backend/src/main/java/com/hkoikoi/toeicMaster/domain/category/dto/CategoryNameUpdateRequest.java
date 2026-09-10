package com.hkoikoi.toeicMaster.domain.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryNameUpdateRequest(

	@NotBlank(message = "카테고리명은 필수입니다.")
	String name
) {
}
