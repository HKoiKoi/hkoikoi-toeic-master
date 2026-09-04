package com.hkoikoi.toeicMaster.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberPageResponse;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberResponse;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberSearchCondition;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberSearchResponse;
import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.hkoikoi.toeicMaster.domain.member.repository.MemberQueryRepository;
import com.hkoikoi.toeicMaster.domain.member.repository.MemberRepository;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;
import com.hkoikoi.toeicMaster.global.util.PageLimitCalculator;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberQueryRepository memberQueryRepository;

	@Transactional(readOnly = true)
	public MemberResponse getMyInfo(Long memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

		return MemberResponse.from(member);
	}

	@Transactional(readOnly = true)
	public MemberPageResponse searchMembers(MemberSearchCondition condition) {

		long offset = (long)(condition.page() - 1) * condition.pageSize();

		Long countLimit = PageLimitCalculator.calculatePageLimit(
			(long)condition.page(),
			(long)condition.pageSize(),
			10L
		);

		List<MemberSearchResponse> members = memberQueryRepository.searchMembers(
			condition,
			offset,
			condition.pageSize()
		);

		Long totalCount = memberQueryRepository.countMembers(condition, countLimit);

		return MemberPageResponse.of(members, totalCount);
	}

	public Member getOrCreateMember(String email, String nickname, OAuth2Provider provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId)
			.orElseGet(() -> {
				String uniqueNickname = generateUniqueNickname(nickname);
				Member newMember = Member.create(email, uniqueNickname, provider, providerId);
				return memberRepository.save(newMember);
			});
	}

	public void updateNickname(Long memberId, String newNickname) {

		if (memberRepository.existsByNickname(newNickname)) {
			throw new BusinessException(ErrorCode.CONFLICT_MEMBER_NICKNAME);
		}

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

		member.updateNickname(newNickname);
	}

	public void updateRole(Long memberId, MemberRole role) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

		member.updateRole(role);
	}

	private String generateUniqueNickname(String baseNickname) {

		String sanitized = baseNickname == null ? "" : baseNickname.replaceAll("[^a-zA-Z0-9가-힣]", "");

		if (sanitized.length() < 2) {
			sanitized = "Member";
		} else if (sanitized.length() > 10) {
			sanitized = sanitized.substring(0, 10);
		}

		if (!memberRepository.existsByNickname(sanitized)) {
			return sanitized;
		}

		String baseForRandom = sanitized.length() > 6 ? sanitized.substring(0, 6) : sanitized;
		String uniqueNickname = baseForRandom;

		while (memberRepository.existsByNickname(uniqueNickname)) {
			int randomNum = (int)(Math.random() * 9000) + 1000;
			uniqueNickname = baseForRandom + randomNum;
		}

		return uniqueNickname;
	}
}
