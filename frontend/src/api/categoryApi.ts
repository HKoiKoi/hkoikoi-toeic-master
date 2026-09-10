import { api } from "@/api/apiInstance";
import type { ApiResponse } from "@/types/api";
import type {
  CategoryTreeResponse,
  CategoryCreateRequest,
  CategoryCreateResponse,
  CategoryNameUpdateRequest,
  CategoryPositionUpdateRequest,
} from "@/types/category";

export const categoryApi = {
  /**
   * 특정 교재의 전체 목차 트리 조회 API
   * @param bookId 목차를 조회할 대상 교재의 PK (식별자)
   */
  getCategoryTree: async (bookId: number) => {
    // 백엔드에서 리스트 형태로 반환하므로 CategoryTreeResponse[] 사용
    const response = await api.get<ApiResponse<CategoryTreeResponse[]>>(
      `/api/v1/books/${bookId}/categories`,
    );

    return response.data;
  },

  /**
   * 단일 목차 노드 생성 API (관리자 전용)
   * @param bookId 목차를 추가할 교재의 PK (식별자)
   * @param data 생성할 목차 정보
   */
  createCategory: async (bookId: number, data: CategoryCreateRequest) => {
    const response = await api.post<ApiResponse<CategoryCreateResponse>>(
      `/api/v1/books/${bookId}/categories`,
      data,
    );

    return response.data;
  },

  /**
   * 목차명 변경 API (관리자 전용)
   * @param bookId 대상 교재 PK (식별자)
   * @param categoryId 이름을 변경할 대상 목차 PK (식별자)
   * @param data 변경할 목차명 객체
   */
  updateCategoryName: async (
    bookId: number,
    categoryId: number,
    data: CategoryNameUpdateRequest,
  ) => {
    const response = await api.patch<ApiResponse<void>>(
      `/api/v1/books/${bookId}/categories/${categoryId}/name`,
      data,
    );

    return response.data;
  },

  /**
   * 목차 위치(DnD 이동 등) 변경 API (관리자 전용)
   * @param bookId 대상 교재 PK (식별자)
   * @param categoryId 위치를 변경할 대상 목차 PK (식별자)
   * @param data 변경될 부모 노드 ID 및 표시 순서
   */
  updateCategoryPosition: async (
    bookId: number,
    categoryId: number,
    data: CategoryPositionUpdateRequest,
  ) => {
    const response = await api.patch<ApiResponse<void>>(
      `/api/v1/books/${bookId}/categories/${categoryId}/position`,
      data,
    );

    return response.data;
  },

  /**
   * 특정 목차 삭제 API (관리자 전용)
   * 하위 자손 노드들까지 일괄 삭제됨
   * @param bookId 대상 교재 PK (식별자)
   * @param categoryId 삭제할 대상 목차 PK (식별자)
   */
  deleteCategory: async (bookId: number, categoryId: number) => {
    const response = await api.delete<ApiResponse<void>>(
      `/api/v1/books/${bookId}/categories/${categoryId}`,
    );

    return response.data;
  },
};
