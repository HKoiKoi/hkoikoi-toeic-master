/**
 * 교재 타입 정의
 */
export type BookType =
  | "VOCA" // 단어장
  | "GRAMMAR" // 문법
  | "RC" // 독해 (RC)
  | "LC" // 듣기 (LC)
  | "COMPREHENSIVE" // 종합서 (RC + LC)
  | "PRACTICE"; // 실전 모의고사

/**
 * 교재 생성 요청을 나타내는 인터페이스
 */
export interface BookCreateRequest {
  title: string;
  publisher?: string;
  bookType: BookType;
}

/**
 * 교재 생성 응답을 나타내는 인터페이스
 */
export interface BookCreateResponse {
  bookId: number;
}

/**
 * 교재 수정 요청을 나타내는 인터페이스
 */
export interface BookUpdateRequest {
  title: string;
  publisher?: string;
  bookType: BookType;
  isActive: boolean;
}

/**
 * 교재 검색 조건을 나타내는 인터페이스
 */
export interface BookSearchCondition {
  title?: string;
  bookType?: BookType;
  startDate?: string;
  endDate?: string;
  page?: number;
  pageSize?: number;
}

/**
 * 교재 검색 결과를 나타내는 인터페이스
 */
export interface BookSearchResponse {
  bookId: number;
  title: string;
  publisher?: string;
  bookType: BookType;
  isActive: boolean;
  createdAt: string;
}

/**
 * 교재 페이지 응답을 나타내는 인터페이스
 */
export interface BookPageResponse {
  books: BookSearchResponse[];
  totalCount: number;
}
