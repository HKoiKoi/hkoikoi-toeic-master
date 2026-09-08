import { Link } from "lucide-react";

export const AdminHome = () => {
  return (
    <div className="p-6 lg:p-8 flex flex-col gap-8">
      {/* 페이지 헤더 */}
      <div>
        <h1 className="text-3xl font-bold text-base-content">대시보드 개요</h1>
        <p className="text-base-content/70 mt-2">
          ToeicMaster 관리자 대시보드에 오신 것을 환영합니다.
        </p>
      </div>

      {/* 상단 요약 통계 */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {/* 통계 카드 1: 전체 회원 */}
        <div className="stat bg-base-100 shadow-sm rounded-2xl border border-base-200">
          <div className="stat-figure text-primary">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              className="inline-block w-8 h-8 stroke-current"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
              />
            </svg>
          </div>
          <div className="stat-title">전체 회원 수</div>
          <div className="stat-value">1,240</div>
          <div className="stat-desc text-success">↗︎ 40 (이번 달)</div>
        </div>

        {/* 통계 카드 2: PRO 사용자 */}
        <div className="stat bg-base-100 shadow-sm rounded-2xl border border-base-200">
          <div className="stat-figure text-secondary">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              className="inline-block w-8 h-8 stroke-current"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M13 10V3L4 14h7v7l9-11h-7z"
              />
            </svg>
          </div>
          <div className="stat-title">PRO 사용자</div>
          <div className="stat-value">382</div>
          <div className="stat-desc">전체 회원의 약 30%</div>
        </div>

        {/* 통계 카드 3: 신규 단어장(Voca) 생성 */}
        <div className="stat bg-base-100 shadow-sm rounded-2xl border border-base-200">
          <div className="stat-figure text-accent">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              className="inline-block w-8 h-8 stroke-current"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"
              />
            </svg>
          </div>
          <div className="stat-title">학습 중인 단어장</div>
          <div className="stat-value">4,120</div>
          <div className="stat-desc">↗︎ 120 (오늘)</div>
        </div>

        {/* 통계 카드 4: 서버 상태 */}
        <div className="stat bg-base-100 shadow-sm rounded-2xl border border-base-200">
          <div className="stat-figure text-info">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              className="inline-block w-8 h-8 stroke-current"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M5 12h14M5 12a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v4a2 2 0 01-2 2M5 12a2 2 0 00-2 2v4a2 2 0 002 2h14a2 2 0 002-2v-4a2 2 0 00-2-2m-2-4h.01M17 16h.01"
              />
            </svg>
          </div>
          <div className="stat-title">서버 상태</div>
          <div className="stat-value text-success">정상</div>
          <div className="stat-desc">API 응답시간 45ms</div>
        </div>
      </div>

      {/* 메인 콘텐츠 영역 */}
      <div className="grid grid-cols-1 xl:grid-cols-3 gap-6">
        <div className="card bg-base-100 shadow-sm border border-base-200 xl:col-span-2">
          <div className="card-body">
            <h2 className="card-title">주간 가입자 추이</h2>
            <div className="flex-1 min-h-75 flex items-center justify-center bg-base-200/50 rounded-xl mt-4 border border-dashed border-base-300">
              <span className="text-base-content/50 font-medium">
                📊 차트 영역 (Recharts 또는 Chart.js 연동 예정)
              </span>
            </div>
          </div>
        </div>

        <div className="card bg-base-100 shadow-sm border border-base-200">
          <div className="card-body">
            <h2 className="card-title mb-4">최근 가입 회원</h2>

            <div className="flex flex-col gap-4">
              <div className="flex items-center gap-4">
                <div className="avatar placeholder">
                  <div className="bg-primary text-primary-content w-10 rounded-full">
                    <span>A</span>
                  </div>
                </div>
                <div className="flex-1">
                  <div className="font-bold text-sm">alice@gmail.com</div>
                  <div className="text-xs text-base-content/60">
                    Google • 방금 전
                  </div>
                </div>
                <div className="badge badge-primary badge-sm">PRO</div>
              </div>

              <div className="flex items-center gap-4">
                <div className="avatar placeholder">
                  <div className="bg-neutral text-neutral-content w-10 rounded-full">
                    <span>B</span>
                  </div>
                </div>
                <div className="flex-1">
                  <div className="font-bold text-sm">bob_k@kakao.com</div>
                  <div className="text-xs text-base-content/60">
                    Kakao • 15분 전
                  </div>
                </div>
                <div className="badge badge-ghost badge-sm">BASIC</div>
              </div>

              <div className="flex items-center gap-4">
                <div className="avatar placeholder">
                  <div className="bg-success text-success-content w-10 rounded-full">
                    <span>C</span>
                  </div>
                </div>
                <div className="flex-1">
                  <div className="font-bold text-sm">charlie99@naver.com</div>
                  <div className="text-xs text-base-content/60">
                    Naver • 1시간 전
                  </div>
                </div>
                <div className="badge badge-ghost badge-sm">BASIC</div>
              </div>
            </div>

            <div className="card-actions justify-end mt-6">
              <Link to="/admin/members" className="btn btn-sm btn-ghost">
                회원 관리로 이동 ➔
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
