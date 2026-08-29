package com.hkoikoi.toeicMaster.domain.member.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberNicknameUpdateRequest;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberResponse;
import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.hkoikoi.toeicMaster.domain.member.service.MemberService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MemberService memberService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser
	@DisplayName(value = "내 정보 조회 요청을 하면 200 OK와 함께 내 정보를 반환한다.")
	void getMyInfo_should_returnMyInfo_when_requested() throws Exception {

		// given
		Long currentMemberId = 1L;

		String email = "test@test.com";
		String nickname = "tester";
		OAuth2Provider provider = OAuth2Provider.GOOGLE;
		String providerId = "google123";
		MemberRole role = MemberRole.BASIC;

		MemberResponse response = new MemberResponse(
			email,
			nickname,
			provider,
			providerId,
			role
		);

		given(memberService.getMyInfo(currentMemberId))
			.willReturn(response);

		// when & then
		mockMvc.perform(get("/api/v1/members/me")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result").value(Boolean.TRUE))
			.andExpect(jsonPath("$.data.email").value(email))
			.andExpect(jsonPath("$.data.nickname").value(nickname))
			.andExpect(jsonPath("$.data.provider").value(provider.toString()))
			.andExpect(jsonPath("$.data.providerId").value(providerId))
			.andExpect(jsonPath("$.data.role").value(role.toString()));
	}

	@Test
	@WithMockUser
	@DisplayName(value = "내 닉네임 변경 요청이 성공하면 200 OK를 반환한다.")
	void updateNickname_should_return200_when_requested() throws Exception {

		// given
		Long currentMemberId = 1L;
		String newNickname = "newNick";
		MemberNicknameUpdateRequest request = new MemberNicknameUpdateRequest(newNickname);

		willDoNothing().given(memberService).updateNickname(currentMemberId, newNickname);

		// when & then
		mockMvc.perform(patch("/api/v1/members/me/nickname")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result").value(Boolean.TRUE))
			.andExpect(jsonPath("$.data").doesNotExist());

		verify(memberService).updateNickname(currentMemberId, newNickname);
	}
}
