package com.hkoikoi.toeicMaster.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberResponse;
import com.hkoikoi.toeicMaster.domain.member.entity.Member;
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

	// TODO: 내 닉네임 변경 로직 작성

	// TODO: [ADMIN] 사용자 닉네임 변경 로직 작성

	// TODO: [ADMIN] 사용자 권한 변경 로직 작성

	private String generateUniqueNickname(String baseNickname) {

		String nickname = (baseNickname != null && !baseNickname.isBlank()) ? baseNickname : "Member";

		if (nickname.length() > 6) {
			nickname = nickname.substring(0, 6);
		}

		String uniqueNickname = nickname;
		while (memberRepository.existsByNickname(uniqueNickname)) {
			int randomNum = (int)(Math.random() * 9000) + 1000;
			uniqueNickname = nickname + randomNum;
		}

		return uniqueNickname;
	}
}
