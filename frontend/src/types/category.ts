/**
 * 목차 생성 요청을 나타내는 인터페이스
 */
export interface CategoryCreateRequest {
  parentId?: number;
  name: string;
  displayOrder: number;
}

/**
 * 목차 생성 응답을 나타내는 인터페이스
 */
export interface CategoryCreateResponse {
  categoryId: number;
}

/**
 * 목차명 수정 요청을 나타내는 인터페이스
 */
export interface CategoryNameUpdateRequest {
  name: string;
}

/**
 * 목차 위치 수정 요청을 나타내는 인터페이스
 */
export interface CategoryPositionUpdateRequest {
  parentId?: number;
  displayOrder: number;
}

/**
 * 목차 트리 응답을 나타내는 인터페이스
 */
export interface CategoryTreeResponse {
  categoryId: number;
  name: string;
  depth: number;
  displayOrder: number;
  children: CategoryTreeResponse[];
}
