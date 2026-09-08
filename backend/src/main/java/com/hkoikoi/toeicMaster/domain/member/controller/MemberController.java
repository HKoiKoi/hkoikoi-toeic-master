package com.hkoikoi.toeicMaster.domain.member.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberNicknameUpdateRequest;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberPageResponse;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberResponse;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberRoleUpdateRequest;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberSearchCondition;
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
		@AuthenticationPrincipal Long currentMemberId
	) {
		return ApiResponse.success(memberService.getMyInfo(currentMemberId));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<MemberPageResponse> searchMembers(
		@ModelAttribute MemberSearchCondition condition
	) {
		return ApiResponse.success(memberService.searchMembers(condition));
	}

	@PatchMapping("/me/nickname")
	public ApiResponse<Void> updateNickname(
		@AuthenticationPrincipal Long currentMemberId,
		@Valid @RequestBody MemberNicknameUpdateRequest request
	) {

		memberService.updateNickname(currentMemberId, request.nickname());

		return ApiResponse.success();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{memberId}/role")
	public ApiResponse<Void> updateRole(
		@PathVariable Long memberId,
		@Valid @RequestBody MemberRoleUpdateRequest request
	) {

		memberService.updateRole(memberId, request.role());

		return ApiResponse.success();
	}
}
