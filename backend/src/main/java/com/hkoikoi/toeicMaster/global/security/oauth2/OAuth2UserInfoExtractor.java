package com.hkoikoi.toeicMaster.global.security.oauth2;

import java.util.Map;

import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.hkoikoi.toeicMaster.global.exception.BusinessException;
import com.hkoikoi.toeicMaster.global.exception.ErrorCode;

public class OAuth2UserInfoExtractor {

	public static OAuth2UserInfo extract(OAuth2Provider provider, Map<String, Object> attributes) {
		return switch (provider) {
			case KAKAO -> {

				if (
					attributes.get("kakao_account") instanceof Map<?, ?> kakaoAccount &&
						kakaoAccount.get("profile") instanceof Map<?, ?> profile
				) {

					yield new OAuth2UserInfo(
						String.valueOf(attributes.get("id")),
						String.valueOf(kakaoAccount.get("email")),
						String.valueOf(profile.get("nickname"))
					);
				}

				throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
			}
			case NAVER -> {

				if (attributes.get("response") instanceof Map<?, ?> response) {

					yield new OAuth2UserInfo(
						String.valueOf(response.get("id")),
						String.valueOf(response.get("email")),
						String.valueOf(response.get("nickname"))
					);
				}

				throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
			}
			case GOOGLE -> new OAuth2UserInfo(

				String.valueOf(attributes.get("sub")),
				String.valueOf(attributes.get("email")),
				String.valueOf(attributes.get("name"))
			);
			case GITHUB -> new OAuth2UserInfo(

				String.valueOf(attributes.get("id")),
				String.valueOf(attributes.get("email")),
				String.valueOf(attributes.get("login"))
			);
		};
	}

	public record OAuth2UserInfo(

		String providerId,
		String email,
		String nickname
	) {
	}
}
