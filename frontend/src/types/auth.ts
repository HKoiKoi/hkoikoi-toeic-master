/**
 * Refresh Token을 이용한 Access Token 재발급 요청을 나타내는 인터페이스
 */
export interface TokenRefreshRequest {
  refreshToken: string;
}

/**
 * Access Token 재발급 요청에 대한 응답을 나타내는 인터페이스
 */
export interface TokenRefreshResponse {
  accessToken: string;
}
