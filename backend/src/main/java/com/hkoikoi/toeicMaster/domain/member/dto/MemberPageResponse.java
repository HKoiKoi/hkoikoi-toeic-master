package com.hkoikoi.toeicMaster.domain.member.dto;

import java.util.List;

public record MemberPageResponse(

	List<MemberSearchResponse> members,
	Long totalCount
) {

	public static MemberPageResponse of(List<MemberSearchResponse> members, Long totalCount) {
		return new MemberPageResponse(members, totalCount);
	}
}
