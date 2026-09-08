import logo from "@/assets/logo.png";
import { authApi } from "@/api/authApi";
import { Menu, Pencil } from "lucide-react";
import { memberApi } from "@/api/memberApi";
import { useNavigate } from "react-router-dom";
import { alertUtils } from "@/utils/alertUtils";
import { useAuthStore } from "@/store/authStore";
import { ProviderBadge, RoleBadge } from "@/components/common/Badges";

export const Header = () => {
  const navigate = useNavigate();
  const { isAuthenticated, member, clearAuth, updateMember } = useAuthStore();

  // 닉네임 수정 로직
  const handleEditNickname = async () => {
    if (!member) return;

    const newNickname = await alertUtils.prompt(
      "닉네임 수정",
      "새로운 닉네임을 입력하세요",
      member.nickname,
      (value) => {
        // 입력값이 비어있거나 공백만 있는 경우
        if (!value || value.trim() === "") {
          return "변경할 닉네임을 입력해 주세요.";
        }

        // 입력값이 기존 닉네임과 동일한 경우
        if (value === member.nickname) {
          return "기존 닉네임과 동일합니다.";
        }

        // 닉네임 길이 제한: 2자 이상 10자 이하
        if (value.length < 2 || value.length > 10) {
          return "닉네임은 2자 이상 10자 이하로 입력해야 합니다.";
        }

        // 닉네임에 특수문자나 공백이 포함되어 있는지 확인
        const regex = /^[a-zA-Z0-9가-힣]+$/;
        if (!regex.test(value)) {
          return "닉네임은 특수문자나 공백 없이 숫자, 한글, 영어만 사용할 수 있습니다.";
        }
      },
      "수정",
      "취소",
    );

    if (newNickname) {
      try {
        await memberApi.updateNickname({ nickname: newNickname });
        updateMember({ nickname: newNickname });
        alertUtils.success("성공", "닉네임이 성공적으로 변경되었습니다.");
      } catch (error) {
        console.error("닉네임 변경 실패:", error);
        alertUtils.error(
          "오류",
          "닉네임 변경에 실패했습니다. 다시 시도해주세요.",
        );
      }
    }
  };

  // 로그아웃 로직
  const handleLogout = async () => {
    const isConfirmed = await alertUtils.confirm(
      "로그아웃",
      "정말 로그아웃 하시겠습니까?",
    );
    if (!isConfirmed) return;

    try {
      await authApi.logout();
      clearAuth();
      await alertUtils.success(
        "안녕히 가세요!",
        "성공적으로 로그아웃 되었습니다.",
      );
      navigate("/");
    } catch (error) {
      console.error("로그아웃 실패:", error);
      alertUtils.error("오류", "로그아웃 처리 중 문제가 발생했습니다.");
    }
  };

  // 공통 네비게이션 메뉴
  const navLinks = [
    { name: "단어장", href: "/voca", isAdmin: false },
    { name: "핵심 문법", href: "/grammar", isAdmin: false },
  ];

  // 관리자(ADMIN) 전용 메뉴 추가
  if (isAuthenticated && member?.role === "ADMIN") {
    navLinks.push({ name: "관리자 대시보드", href: "/admin", isAdmin: true });
  }

  const renderMemberProfile = () => {
    if (!member) return null;

    return (
      <li className="menu-title flex flex-col gap-2 border-b border-base-200 pb-4 mb-2 px-4">
        {/* 닉네임 및 수정 이모지 */}
        <div className="flex items-center gap-1">
          <span className="font-bold text-lg text-base-content">
            {member.nickname}
          </span>
          <button
            onClick={handleEditNickname}
            className="btn btn-xs btn-ghost btn-circle text-gray-400 hover:text-primary"
            title="닉네임 수정"
          >
            <Pencil size={14} />
          </button>
        </div>

        {/* OAuth2 공급자 */}
        <div>
          <ProviderBadge provider={member.provider} />
        </div>

        {/* 이메일 */}
        <div className="text-sm text-gray-500">{member.email}</div>

        {/* 권한 배지 */}
        <div>
          <RoleBadge role={member.role} />
        </div>
      </li>
    );
  };

  return (
    <header className="bg-base-100 shadow-sm w-full">
      <div className="navbar max-w-7xl mx-auto w-full px-4 sm:px-6 lg:px-8">
        {/* 로고 */}
        <div className="navbar-start">
          <a
            href="/"
            className="flex items-center text-xl font-bold hover:opacity-80 transition-opacity gap-1"
          >
            <img
              src={logo}
              alt="로고"
              className="w-8 h-8 object-contain rounded-2xl"
            />
            <span className="hidden sm:block">HKoiKoi's TOEIC Master</span>
          </a>
        </div>

        {/* 데스크탑용 메뉴 */}
        <div className="navbar-center hidden lg:flex">
          {isAuthenticated && (
            <ul className="menu menu-horizontal px-1 gap-2">
              {navLinks.map((link) => (
                <li key={link.name}>
                  <a
                    href={link.href}
                    className={
                      link.isAdmin
                        ? "text-primary font-bold border border-primary/50 bg-primary/5 hover:bg-primary/10 hover:border-primary rounded-lg px-3 py-2 transition-all"
                        : "font-medium hover:text-primary px-3 py-2"
                    }
                  >
                    {link.name}
                  </a>
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* 데스크탑: 로그인/프로필, 모바일: 햄버거 메뉴 */}
        <div className="navbar-end gap-2">
          {/* 데스트탑 환경: 로그인 버튼 또는 프로필 드롭다운 */}
          {!isAuthenticated ? (
            <a href="/login" className="btn btn-primary btn-sm md:btn-md">
              로그인
            </a>
          ) : (
            <>
              {/* 데스크탑 환경: 로그인 상태일 때 프로필 드롭다운 */}
              <div className="hidden lg:flex dropdown dropdown-end">
                <div
                  tabIndex={0}
                  role="button"
                  className="btn btn-ghost btn-sm md:btn-md m-1"
                >
                  {member?.nickname}
                </div>
                <ul
                  tabIndex={0}
                  className="dropdown-content menu bg-base-100 rounded-box z-1 w-64 p-2 shadow-lg border border-base-200 mt-4"
                >
                  {renderMemberProfile()}
                  <li>
                    <button
                      onClick={handleLogout}
                      className="text-error font-medium hover:bg-error/10"
                    >
                      로그아웃
                    </button>
                  </li>
                </ul>
              </div>

              {/* 모바일 환경: 오른쪽 햄버거 토글 메뉴 */}
              <div className="dropdown dropdown-end lg:hidden">
                <div tabIndex={0} role="button" className="btn btn-ghost p-1">
                  <Menu size={28} />
                </div>
                <ul
                  tabIndex={0}
                  className="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-56 p-2 shadow-lg border border-base-200"
                >
                  {/* 로그인 했을 때 표시 */}
                  {renderMemberProfile()}

                  <div className="divider my-0"></div>

                  {/* 네비게이션 메뉴 */}
                  {navLinks.map((link) => (
                    <li key={link.name}>
                      <a
                        href={link.href}
                        className={`py-3 ${link.isAdmin ? "text-primary font-bold bg-primary/5" : ""}`}
                      >
                        {link.name}
                      </a>
                    </li>
                  ))}

                  <div className="divider my-1"></div>

                  {/* 로그아웃 메뉴 */}
                  <li>
                    <button onClick={handleLogout} className="text-error py-3">
                      로그아웃
                    </button>
                  </li>
                </ul>
              </div>
            </>
          )}
        </div>
      </div>
    </header>
  );
};
