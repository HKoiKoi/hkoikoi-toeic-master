package com.hkoikoi.toeicMaster.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberNicknameUpdateRequest;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberResponse;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberRoleUpdateRequest;
import com.hkoikoi.toeicMaster.domain.member.service.MemberService;
import com.hkoikoi.toeicMaster.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	public ApiResponse<MemberResponse> getMyInfo(
		// TODO: OAuth2 인증 인가 구현 후, 로그인한 사용자 ID를 받아오도록 수정
	) {

		// 임시 ID
		Long currentMemberId = 1L;

		return ApiResponse.success(memberService.getMyInfo(currentMemberId));
	}

	@PatchMapping("/me/nickname")
	public ApiResponse<Void> updateNickname(
		// TODO: OAuth2 인증 인가 구현 후, 로그인한 사용자 ID를 받아오도록 수정
		@Valid @RequestBody MemberNicknameUpdateRequest request
	) {

		Long currentMemberId = 1L;

		memberService.updateNickname(currentMemberId, request.nickname());

		return ApiResponse.success();
	}

	// TODO: ADMIN 전용 권한 제어 추가 필요
	@PatchMapping("/{memberId}/role")
	public ApiResponse<Void> updateRole(
		@PathVariable Long memberId,
		@Valid @RequestBody MemberRoleUpdateRequest request
	) {

		memberService.updateRole(memberId, request.role());

		return ApiResponse.success();
	}
}
