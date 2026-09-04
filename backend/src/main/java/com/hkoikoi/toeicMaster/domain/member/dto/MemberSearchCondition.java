package com.hkoikoi.toeicMaster.domain.member.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;

public record MemberSearchCondition(

	String email,

	String nickname,

	MemberRole role,

	OAuth2Provider provider,

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate,

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate,

	Integer page,

	Integer pageSize
) {

	public MemberSearchCondition {

		if (page == null || page < 1) {
			page = 1;
		}

		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
	}
}
