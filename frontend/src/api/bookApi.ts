import { api } from "@/api/apiInstance";
import type { ApiResponse } from "@/types/api";
import type {
  BookPageResponse,
  BookUpdateRequest,
  BookCreateRequest,
  BookCreateResponse,
  BookSearchCondition,
} from "@/types/book";

export const bookApi = {
  /**
   * 교재 목록 조회 API
   * @param condition 검색 조건 및 페이징 정보
   */
  searchBooks: async (condition: BookSearchCondition) => {
    const response = await api.get<ApiResponse<BookPageResponse>>(
      "/api/v1/books",
      { params: condition },
    );

    return response.data;
  },

  /**
   * 신규 교재 생성 API (관리자 전용)
   * @param data 생성할 교재 정보
   */
  createBook: async (data: BookCreateRequest) => {
    const response = await api.post<ApiResponse<BookCreateResponse>>(
      "/api/v1/books",
      data,
    );

    return response.data;
  },

  /**
   * 특정 교재 정보 조회 API (관리자 전용)
   * @param bookId 수정할 대상 교재의 PK (식별자)
   * @param data 변경할 교재 정보 (상태, 제목, 출판사 등)
   */
  updateBook: async (bookId: number, data: BookUpdateRequest) => {
    const response = await api.patch<ApiResponse<void>>(
      `/api/v1/books/${bookId}`,
      data,
    );

    return response.data;
  },

  /**
   * 특정 교재 삭제 API (관리자 전용)
   * @param bookId 삭제할 대상 교재의 PK (식별자)
   */
  deleteBook: async (bookId: number) => {
    const response = await api.delete<ApiResponse<void>>(
      `/api/v1/books/${bookId}`,
    );

    return response.data;
  },
};
