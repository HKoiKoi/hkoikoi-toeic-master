package com.hkoikoi.toeicMaster.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberNicknameUpdateRequest(

	@NotBlank(message = "변경할 닉네임을 입력해 주세요.")
	@Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 특수문자나 공백 없이 숫자, 한글, 영어만 사용할 수 있습니다.")
	String nickname
) {
}
