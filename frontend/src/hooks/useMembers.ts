import { memberApi } from "@/api/memberApi";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import type { MemberRole, MemberSearchCondition } from "@/types/member";

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

/**
 * 회원 권한 변경을 위한 커스텀 훅
 */
export const useUpdateMemberRole = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ memberId, role }: { memberId: number; role: MemberRole }) =>
      memberApi.updateRole(memberId, { role }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["members", "search"] });
    },
  });
};
