package com.hkoikoi.toeicMaster.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberResponse;
import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.hkoikoi.toeicMaster.domain.member.repository.MemberRepository;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MemberResponse getMyInfo(Long memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

		return MemberResponse.from(member);
	}

	@Transactional
	public Member getOrCreateMember(String email, String nickname, OAuth2Provider provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId)
			.orElseGet(() -> {
				String uniqueNickname = generateUniqueNickname(nickname);
				Member newMember = Member.create(email, uniqueNickname, provider, providerId);
				return memberRepository.save(newMember);
			});
	}

	@Transactional
	public void updateNickname(Long memberId, String newNickname) {

		if (memberRepository.existsByNickname(newNickname)) {
			throw new BusinessException(ErrorCode.CONFLICT_MEMBER_NICKNAME);
		}

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

		member.updateNickname(newNickname);
	}

	@Transactional
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
