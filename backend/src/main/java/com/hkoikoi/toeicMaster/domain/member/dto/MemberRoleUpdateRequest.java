package com.hkoikoi.toeicMaster.domain.member.dto;

import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;

import jakarta.validation.constraints.NotNull;

public record MemberRoleUpdateRequest(

	@NotNull(message = "변경할 권한을 입력해 주세요.")
	MemberRole role
) {
}
