package com.hkoikoi.toeicMaster.global.security.oauth2;

import java.util.Locale;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.hkoikoi.toeicMaster.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final MemberService memberService;

	@NonNull
	@Override
	public OAuth2User loadUser(@NonNull OAuth2UserRequest request) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(request);
		Map<String, Object> attributes = oAuth2User.getAttributes();

		String registrationId = request.getClientRegistration().getRegistrationId().toUpperCase(Locale.ROOT);
		OAuth2Provider provider = OAuth2Provider.valueOf(registrationId);

		OAuth2UserInfoExtractor.OAuth2UserInfo userInfo = OAuth2UserInfoExtractor.extract(provider, attributes);

		log.info("OAuth2 로그인 요청: Provider=[{}], Email=[{}]", provider, userInfo.email());

		Member member = memberService.getOrCreateMember(
			userInfo.email(),
			userInfo.nickname(),
			provider,
			userInfo.providerId()
		);

		return new CustomOAuth2User(
			member.getId(),
			member.getRole().toString(),
			attributes
		);
	}
}
