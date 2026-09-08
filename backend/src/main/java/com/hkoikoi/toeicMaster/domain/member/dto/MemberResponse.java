package com.hkoikoi.toeicMaster.domain.member.dto;

import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;

public record MemberResponse(

	String email,
	String nickname,
	OAuth2Provider provider,
	String providerId,
	MemberRole role
) {

	public static MemberResponse from(Member member) {
		return new MemberResponse(
			member.getEmail(),
			member.getNickname(),
			member.getProvider(),
			member.getProviderId(),
			member.getRole()
		);
	}
}
