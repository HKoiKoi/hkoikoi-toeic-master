import { api } from "@/api/apiInstance";
import type { ApiResponse } from "@/types/api";

export const authApi = {
  /**
   * 로그아웃 API
   * 백엔드 세션(Redis Refresh Token) 삭제 및 브라우저 쿠키 무효화
   */
  logout: async () => {
    const response = await api.post<ApiResponse<void>>("/api/v1/auth/logout");
    return response.data;
  },
};
