import { useState } from "react";
import logo from "@/assets/logo.png";
import { Menu, Pencil } from "lucide-react";
import { ProviderBadge, RoleBadge } from "@/components/common/Badges";

export const Header = () => {
  // TODO: Zustand, React Query를 통해 전역 상태로 관리할 유저 정보
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(true); // 임시로 로그인 상태를 true/false로 변경하며 확인

  // TODO: 백엔드 API 연동 시 실제 사용자 데이터로 교체
  const mockMember = {
    nickname: "토익마스터",
    email: "master@hkoikoi.dev",
    provider: "GOOGLE",
    role: "ADMIN",
  };

  // TODO: 닉네임 수정 로직
  const handleEditNickname = () => {
    console.log("닉네임 수정 로직 실행");
  };

  // TODO: 로그아웃 로직
  const handleLogout = () => {
    console.log("로그아웃 API 호출 및 상태 초기화");
    setIsLoggedIn(false);
  };

  // 공통 네비게이션 메뉴
  const navLinks = [
    { name: "단어장", href: "/voca", isAdmin: false },
    { name: "핵심 문법", href: "/grammar", isAdmin: false },
  ];

  // 관리자(ADMIN) 전용 메뉴 추가
  if (isLoggedIn && mockMember.role === "ADMIN") {
    navLinks.push({ name: "관리자 대시보드", href: "/admin", isAdmin: true });
  }

  const renderMemberProfile = () => (
    <li className="menu-title flex flex-col gap-2 border-b border-base-200 pb-4 mb-2 px-4">
      {/* 닉네임 및 수정 이모지 */}
      <div className="flex items-center gap-1">
        <span className="font-bold text-lg text-base-content">
          {mockMember.nickname}
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
        <ProviderBadge provider={mockMember.provider} />
      </div>

      {/* 이메일 */}
      <div className="text-sm text-gray-500">{mockMember.email}</div>

      {/* 권한 배지 */}
      <div>
        <RoleBadge role={mockMember.role} />
      </div>
    </li>
  );

  return (
    <header className="navbar bg-base-100 shadow-sm px-4 lg:px-8">
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
      </div>

      {/* 데스크탑: 로그인/프로필, 모바일: 햄버거 메뉴 */}
      <div className="navbar-end gap-2">
        {/* 데스트탑 환경: 로그인 버튼 또는 프로필 드롭다운 */}
        <div className="hidden lg:flex">
          {!isLoggedIn ? (
            <a href="/login" className="btn btn-primary btn-sm md:btn-md">
              로그인
            </a>
          ) : (
            <div className="dropdown dropdown-end">
              <div
                tabIndex={0}
                role="button"
                className="btn btn-ghost btn-sm md:btn-md m-1"
              >
                {mockMember.nickname}
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
          )}
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
            {isLoggedIn && renderMemberProfile()}

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

            {/* 로그인/로그아웃 메뉴 */}
            {!isLoggedIn ? (
              <li>
                <a href="/login" className="text-primary font-bold py-3">
                  로그인
                </a>
              </li>
            ) : (
              <>
                <li>
                  <button onClick={handleEditNickname} className="py-3">
                    닉네임 수정
                  </button>
                </li>
                <li>
                  <button onClick={handleLogout} className="text-error py-3">
                    로그아웃
                  </button>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </header>
  );
};
