import { useState } from "react";
import { alertUtils } from "@/utils/alertUtils";
import { RoleBadge, ProviderBadge } from "@/components/common/Badges";
import { useSearchMembers, useUpdateMemberRole } from "@/hooks/useMembers";
import type {
  MemberRole,
  OAuth2Provider,
  MemberSearchCondition,
} from "@/types/member";
import { BaseSelect } from "@/components/common/BaseSelect";
import { Pagination } from "@/components/common/Pagination";

const roleOptions = [
  { value: "BASIC", label: "BASIC" },
  { value: "PRO", label: "PRO" },
  { value: "ADMIN", label: "ADMIN" },
];

export const AdminMembers = () => {
  // 검색 및 페이징 상태 관리
  const [condition, setCondition] = useState<MemberSearchCondition>({
    page: 1,
    pageSize: 10,
  });

  // 검색어 입력용 임시 상태
  const [searchInput, setSearchInput] = useState({
    email: "",
    nickname: "",
    role: "" as MemberRole | "",
    provider: "" as OAuth2Provider | "",
  });

  // Member 커스텀 훅 사용
  const { data, isLoading, isError } = useSearchMembers(condition);
  const updateRoleMutation = useUpdateMemberRole();

  // 검색 핸들러
  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setCondition({
      page: 1,
      pageSize: 10,
      email: searchInput.email || undefined,
      nickname: searchInput.nickname || undefined,
      role: searchInput.role || undefined,
      provider: searchInput.provider || undefined,
    });
  };

  // 페이지 이동 핸들러
  const handlePageChange = (newPage: number) => {
    setCondition((prev) => ({ ...prev, page: newPage }));
  };

  // 권한 변경 핸들러
  const handleRoleChange = (memberId: number, newRole: MemberRole) => {
    alertUtils
      .confirm(
        "권한 변경",
        `이 회원의 권한을 ${newRole}(으)로 변경하시겠습니까?`,
      )
      .then((isConfirmed) => {
        if (isConfirmed) {
          updateRoleMutation.mutate(
            { memberId, role: newRole },
            {
              onSuccess: () =>
                alertUtils.success("성공", "권한이 변경되었습니다."),
              onError: () =>
                alertUtils.error("실패", "권한 변경에 실패했습니다."),
            },
          );
        }
      });
  };

  return (
    <div className="p-6 lg:p-8 flex flex-col gap-6">
      {/* 타이틀 영역 */}
      <div>
        <h1 className="text-3xl font-bold text-base-content">회원 관리</h1>
        <p className="text-base-content/70 mt-2">
          전체 가입 회원을 조회하고 권한을 관리합니다.
        </p>
      </div>

      {/* 검색 필터 영역 */}
      <div className="card bg-base-100 shadow-sm border border-base-200">
        <div className="card-body p-4 lg:p-6">
          <form
            onSubmit={handleSearch}
            className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4 items-end"
          >
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text font-medium">이메일</span>
              </label>
              <input
                type="text"
                placeholder="이메일 입력"
                className="input input-bordered w-full"
                value={searchInput.email}
                onChange={(e) =>
                  setSearchInput({ ...searchInput, email: e.target.value })
                }
              />
            </div>
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text font-medium">닉네임</span>
              </label>
              <input
                type="text"
                placeholder="닉네임 입력"
                className="input input-bordered w-full"
                value={searchInput.nickname}
                onChange={(e) =>
                  setSearchInput({ ...searchInput, nickname: e.target.value })
                }
              />
            </div>
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text font-medium">권한</span>
              </label>
              <select
                className="select select-bordered w-full"
                value={searchInput.role}
                onChange={(e) =>
                  setSearchInput({
                    ...searchInput,
                    role: e.target.value as MemberRole,
                  })
                }
              >
                <option value="">전체</option>
                <option value="BASIC">BASIC</option>
                <option value="PRO">PRO</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </div>
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text font-medium">플랫폼</span>
              </label>
              <select
                className="select select-bordered w-full"
                value={searchInput.provider}
                onChange={(e) =>
                  setSearchInput({
                    ...searchInput,
                    provider: e.target.value as OAuth2Provider,
                  })
                }
              >
                <option value="">전체</option>
                <option value="GOOGLE">GOOGLE</option>
                <option value="KAKAO">KAKAO</option>
                <option value="NAVER">NAVER</option>
                <option value="GITHUB">GITHUB</option>
              </select>
            </div>
            <div className="flex gap-2 w-full mt-4 lg:mt-0">
              <button type="submit" className="btn btn-primary flex-1">
                검색
              </button>
              <button
                type="button"
                className="btn btn-ghost flex-1"
                onClick={() => {
                  setSearchInput({
                    email: "",
                    nickname: "",
                    role: "",
                    provider: "",
                  });
                  setCondition({ page: 1, pageSize: 10 });
                }}
              >
                초기화
              </button>
            </div>
          </form>
        </div>
      </div>

      {/* 테이블 영역 */}
      <div className="card bg-base-100 shadow-sm border border-base-200 overflow-hidden">
        <div className="overflow-x-auto">
          <table className="table table-zebra w-full">
            <thead className="bg-base-200/50 text-base-content">
              <tr>
                <th>ID</th>
                <th>플랫폼</th>
                <th>이메일 / 닉네임</th>
                <th>가입일</th>
                <th>권한</th>
                <th className="text-center">권한 변경</th>
              </tr>
            </thead>

            <tbody>
              {isLoading ? (
                <tr>
                  <td colSpan={6} className="text-center py-10">
                    <span className="loading loading-spinner loading-md text-primary"></span>
                  </td>
                </tr>
              ) : isError ? (
                <tr>
                  <td colSpan={6} className="text-center py-10 text-error">
                    데이터를 불러오는 중 오류가 발생했습니다.
                  </td>
                </tr>
              ) : data?.data?.members.length === 0 ? (
                <tr>
                  <td
                    colSpan={6}
                    className="text-center py-10 text-base-content/60"
                  >
                    검색 결과가 없습니다.
                  </td>
                </tr>
              ) : (
                data?.data?.members.map((member) => (
                  <tr key={member.memberId}>
                    <td className="font-medium text-base-content/70">
                      {member.memberId}
                    </td>
                    <td>
                      <ProviderBadge provider={member.provider} />{" "}
                    </td>
                    <td>
                      <div className="flex flex-col gap-0.5">
                        <span className="font-bold text-base-content">
                          {member.nickname}
                        </span>
                        <span className="text-xs text-base-content/60">
                          {member.email}
                        </span>
                      </div>
                    </td>
                    <td className="text-sm text-base-content/80">
                      {member.createdAt.split(" ")[0]}
                    </td>
                    <td>
                      <RoleBadge role={member.role} />
                    </td>
                    <td className="text-center">
                      <BaseSelect
                        className="select-sm w-full min-w-20 max-w-32.5 text-xs sm:text-sm"
                        value={member.role}
                        options={roleOptions}
                        disabled={updateRoleMutation.isPending}
                        onChange={(newRole) =>
                          handleRoleChange(
                            member.memberId,
                            newRole as MemberRole,
                          )
                        }
                      />
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {/* 페이지네이션 영역 */}
        {data?.data && data.data.totalCount > 0 && (
          <div className="flex justify-center p-4 border-t border-base-200 bg-base-100">
            <Pagination
              currentPage={condition.page || 1}
              pageSize={condition.pageSize || 10}
              totalCount={data.data.totalCount}
              onPageChange={handlePageChange}
            />
          </div>
        )}
      </div>
    </div>
  );
};
