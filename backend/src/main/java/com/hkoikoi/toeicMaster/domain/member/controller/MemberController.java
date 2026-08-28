package com.hkoikoi.toeicMaster.domain.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	// TODO: 내 정보 조회 API 매핑 - "/api/v1/members/me"

	// TODO: 내 닉네임 변경 API 매핑 - "/api/v1/members/me/nickname"

	// TODO: [ADMIN] 사용자 닉네임 변경 API 매핑 - "/api/v1/members/{memberId}/nickname"

	// TODO: [ADMIN] 사용자 권한 변경 API 매핑 - "/api/v1/members/{memberId}/role"
}
