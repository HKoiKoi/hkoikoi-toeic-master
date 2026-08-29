package com.hkoikoi.toeicMaster.domain.auth.dto;

public record TokenRefreshResponse(

	String accessToken
) {

	public static TokenRefreshResponse of(String accessToken) {
		return new TokenRefreshResponse(accessToken);
	}
}
