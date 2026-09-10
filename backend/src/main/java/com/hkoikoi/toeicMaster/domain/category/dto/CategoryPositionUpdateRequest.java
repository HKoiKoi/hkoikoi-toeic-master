package com.hkoikoi.toeicMaster.domain.category.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryPositionUpdateRequest(

	Long parentId,

	@NotNull(message = "노출 순서는 필수입니다.")
	Integer displayOrder
) {
}
