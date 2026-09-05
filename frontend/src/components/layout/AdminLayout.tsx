import { Link, Outlet } from "react-router-dom";

export const AdminLayout = () => {
  return (
    <div className="drawer lg:drawer-open">
      <input id="admin-drawer" type="checkbox" className="drawer-toggle" />

      {/* 메인 콘텐츠 영역 */}
      <div className="drawer-content flex flex-col min-h-screen bg-base-200">
        {/* 모바일 전용 상단 네비게이션 바 */}
        <div className="navbar bg-base-100 w-full lg:hidden sticky top-0 z-10 border-b border-base-200 shadow-sm">
          <div className="flex-none">
            <label
              htmlFor="admin-drawer"
              aria-label="open sidebar"
              className="btn btn-square btn-ghost"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                className="inline-block h-6 w-6 stroke-current"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
            </label>
          </div>

          <div className="flex-1">
            <span className="text-xl font-bold px-2">관리자 대시보드</span>
          </div>
        </div>

        {/* 실제 페이지 내용이 렌더링되는 영역 */}
        <main>
          <Outlet />
        </main>
      </div>

      {/* 사이드바 영역 */}
      <div className="drawer-side z-20">
        <label
          htmlFor="admin-drawer"
          aria-label="close sidebar"
          className="drawer-overlay"
        ></label>

        <aside className="bg-base-100 min-h-screen w-72 flex flex-col shadow-xl lg:shadow-none lg:border-r lg:border-base-200">
          {/* 사이드바 상단 로고/타이틀 (데스크탑 전용) */}
          <div className="p-6 hidden lg:flex items-center border-b border-base-200">
            <span className="text-2xl font-bold text-primary">Admin Panel</span>
          </div>

          {/* 메뉴 리스트 */}
          <ul className="menu flex-1 px-4 py-6 gap-2 text-base font-medium">
            <li>
              <Link to="/admin">대시보드 홈</Link>
            </li>
            <li>
              <Link to="/admin/members">회원 관리</Link>
            </li>
            <li>
              <Link to="/admin/settings">시스템 설정</Link>
            </li>
          </ul>

          {/* 사이드바 하단 */}
          <div className="p-4 border-t border-base-200">
            <button className="btn btn-ghost w-full justify-start text-error">
              로그아웃
            </button>
          </div>
        </aside>
      </div>
    </div>
  );
};
