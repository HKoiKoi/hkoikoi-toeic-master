package com.hkoikoi.toeicMaster.global.security.oauth2;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record CustomOAuth2User(

	Long memberId,
	String role,
	Map<String, Object> attributes
) implements OAuth2User {

	@NonNull
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@NonNull
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@NonNull
	@Override
	public String getName() {
		return String.valueOf(memberId);
	}
}
