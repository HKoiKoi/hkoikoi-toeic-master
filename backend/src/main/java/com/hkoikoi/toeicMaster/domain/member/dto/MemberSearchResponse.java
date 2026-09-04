package com.hkoikoi.toeicMaster.domain.member.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;

public record MemberSearchResponse(

	Long memberId,

	String email,

	String nickname,

	MemberRole role,

	OAuth2Provider provider,

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
}
