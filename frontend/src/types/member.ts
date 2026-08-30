/**
 * 사용자 권한 타입 정의
 */
export type MemberRole =
  | "BASIC" // 일반 사용자
  | "PRO" // 심화 사용자
  | "ADMIN"; // 관리자

/**
 * OAuth2 공급자 타입 정의
 */
export type OAuth2Provider =
  | "KAKAO" // 카카오
  | "NAVER" // 네이버
  | "GOOGLE" // 구글
  | "GITHUB"; // 깃허브

/**
 * 사용자 닉네임 수정 요청을 나타내는 인터페이스
 */
export interface MemberNicknameUpdateRequest {
  nickname: string;
}

/**
 * 사용자 권한 수정 요청을 나타내는 인터페이스
 */
export interface MemberRoleUpdateRequest {
  role: MemberRole;
}

/**
 * 사용자 정보를 나타내는 인터페이스
 */
export interface MemberResponse {
  email: string;
  nickname: string;
  provider: OAuth2Provider;
  providerId: string;
  role: MemberRole;
}
