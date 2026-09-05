import { memberApi } from "@/api/memberApi";
import { useQuery } from "@tanstack/react-query";
import type { MemberSearchCondition } from "@/types/member";

/**
 * 회원 검색을 위한 커스텀 훅
 * @param condition 검색 조건 (페이지, 필터, etc.)
 */
export const useSearchMembers = (condition: MemberSearchCondition) => {
  return useQuery({
    queryKey: ["members", "search", condition],
    queryFn: () => memberApi.searchMembers(condition),
  });
};
