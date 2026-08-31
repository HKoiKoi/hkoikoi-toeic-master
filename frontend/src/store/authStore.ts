import { create } from "zustand/react";
import type { MemberResponse } from "@/types/member";

interface AuthState {
  // 상태
  accessToken: string | null;
  member: MemberResponse | null;
  isAuthenticated: boolean;

  // 액션
  setAuth: (accessToken: string, member: MemberResponse) => void;
  clearAuth: () => void;
  updateMember: (partialMember: Partial<MemberResponse>) => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: null,
  member: null,
  isAuthenticated: false,

  // 로그인 성공 시 accessToken과 member 정보를 저장하고 인증 상태를 true로 설정
  setAuth: (accessToken, member) =>
    set({
      accessToken,
      member,
      isAuthenticated: true,
    }),

  // 로그아웃 시 accessToken과 member 정보를 초기화하고 인증 상태를 false로 설정
  clearAuth: () =>
    set({
      accessToken: null,
      member: null,
      isAuthenticated: false,
    }),

  // member 정보 업데이트 (부분 업데이트 가능)
  updateMember: (partialMember) =>
    set((state) => ({
      member: state.member ? { ...state.member, ...partialMember } : null,
    })),
}));
