import { api } from "@/api/apiInstance";
import type { ApiResponse } from "@/types/api";
import type {
  MemberResponse,
  MemberPageResponse,
  MemberSearchCondition,
  MemberRoleUpdateRequest,
  MemberNicknameUpdateRequest,
} from "@/types/member";

export const memberApi = {
  /**
   * 내 프로필 정보 조회 API
   * 로그인 직후 현재 사용자의 정보 가져옴
   */
  getMyInfo: async () => {
    const response =
      await api.get<ApiResponse<MemberResponse>>("/api/v1/members/me");

    return response.data;
  },

  /**
   * 회원 목록 조회 API (관리자 전용)
   * @param condition 검색 조건 및 페이징 정보
   */
  searchMembers: async (condition: MemberSearchCondition) => {
    const response = await api.get<ApiResponse<MemberPageResponse>>(
      "/api/v1/members",
      {
        params: condition,
      },
    );

    return response.data;
  },

  /**
   * 내 닉네임 수정 API
   * @param data 변경할 닉네임 객체
   */
  updateNickname: async (data: MemberNicknameUpdateRequest) => {
    const response = await api.patch<ApiResponse<void>>(
      "/api/v1/members/me/nickname",
      data,
    );

    return response.data;
  },

  /**
   * 회원 권한 변경 (ADMIN 전용)
   * @param memberId 권한을 변경할 대상 회원의 PK (식별자)
   * @param data 변경할 권한 (BASIC, PRO, ADMIN)
   */
  updateRole: async (memberId: number, data: MemberRoleUpdateRequest) => {
    const response = await api.patch<ApiResponse<void>>(
      `/api/v1/members/${memberId}/role`,
      data,
    );

    return response.data;
  },
};
