/**
 * 에러 응답의 상세 정보를 나타내는 인터페이스
 */
export interface FieldErrorDetail {
  field: string;
  message: string;
}

/**
 * API 요청 실패 시 반환되는 에러 응답을 나타내는 인터페이스
 */
export interface ErrorResponse {
  timestamp: string;
  code: string;
  message: string;
  details?: FieldErrorDetail[];
}

/**
 * API 요청에 대한 일반적인 응답 구조를 나타내는 제네릭 인터페이스
 * @template T - 응답 데이터의 타입
 */
export interface ApiResponse<T> {
  result: boolean;
  data?: T;
  error?: ErrorResponse;
}
