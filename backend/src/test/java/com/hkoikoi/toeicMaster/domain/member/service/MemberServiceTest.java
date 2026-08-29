package com.hkoikoi.toeicMaster.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.hkoikoi.toeicMaster.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName(value = "기존 회원이면 DB에 새로 저장하지 않고 기존 회원 정보를 반환한다.")
	void getOrCreateMember_should_returnExistingMember_when_existingMember() {

		// given
		String email = "test@test.com";
		String nickname = "testMember";
		OAuth2Provider provider = OAuth2Provider.KAKAO;
		String providerId = "kakao123";

		Member existingMember = Member.create(email, nickname, provider, providerId);
		given(memberRepository.findByProviderAndProviderId(provider, providerId))
			.willReturn(Optional.of(existingMember));

		// when
		Member resultMember = memberService.getOrCreateMember(email, nickname, provider, providerId);

		// then
		assertThat(resultMember).isNotNull();

		assertThat(resultMember.getEmail()).isEqualTo(email);
		assertThat(resultMember.getNickname()).isEqualTo(nickname);
		assertThat(resultMember.getProvider()).isEqualTo(provider);
		assertThat(resultMember.getProviderId()).isEqualTo(providerId);

		verify(memberRepository, never()).save(existingMember);
	}

	@Test
	@DisplayName(value = "새로운 회원이면서 닉네임이 고유하면 BASIC 권한으로 회원을 생성하여 반환한다.")
	void getOrCreateMember_should_returnNewMember_when_newMemberAndUniqueNickname() {

		// given
		String email = "new@test.com";
		String nickname = "newMember";
		OAuth2Provider provider = OAuth2Provider.GOOGLE;
		String providerId = "google123";

		given(memberRepository.findByProviderAndProviderId(provider, providerId))
			.willReturn(Optional.empty());
		given(memberRepository.existsByNickname(anyString()))
			.willReturn(false);

		given(memberRepository.save(any(Member.class)))
			.willAnswer(invocation -> invocation.getArgument(0));

		// when
		Member resultMember = memberService.getOrCreateMember(email, nickname, provider, providerId);

		// then
		assertThat(resultMember).isNotNull();

		assertThat(resultMember.getEmail()).isEqualTo(email);
		assertThat(resultMember.getNickname()).isEqualTo(nickname);
		assertThat(resultMember.getProvider()).isEqualTo(provider);
		assertThat(resultMember.getProviderId()).isEqualTo(providerId);
		assertThat(resultMember.getRole().name()).isEqualTo("BASIC");

		verify(memberRepository).save(any(Member.class));
	}

	@Test
	@DisplayName(value = "새로운 회원 생성 시 닉네임이 중복되면 랜덤 숫자를 붙여 회원을 생성하여 반환한다.")
	void getOrCreateMember_should_returnNewMemberWithRandomNumber_when_duplicateNickname() {

		// given
		String email = "dup@test.com";
		String nickname = "dupMember";
		OAuth2Provider provider = OAuth2Provider.NAVER;
		String providerId = "naver123";

		given(memberRepository.findByProviderAndProviderId(provider, providerId))
			.willReturn(Optional.empty());
		given(memberRepository.existsByNickname(anyString()))
			.willReturn(true)
			.willReturn(false);

		given(memberRepository.save(any(Member.class)))
			.willAnswer(innovation -> innovation.getArgument(0));

		// when
		Member resultMember = memberService.getOrCreateMember(email, nickname, provider, providerId);

		// then
		assertThat(resultMember).isNotNull();

		assertThat(resultMember.getEmail()).isEqualTo(email);
		assertThat(resultMember.getNickname().substring(0, 6)).isEqualTo(nickname.substring(0, 6));
		assertThat(resultMember.getProvider()).isEqualTo(provider);
		assertThat(resultMember.getProviderId()).isEqualTo(providerId);

		verify(memberRepository, times(2)).existsByNickname(anyString());
		verify(memberRepository).save(any(Member.class));
	}
}