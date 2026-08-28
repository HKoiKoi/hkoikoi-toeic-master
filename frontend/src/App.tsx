import { BookOpen, BookText, LogIn, Menu } from "lucide-react";

function App() {
  return (
    <div className="min-h-screen flex flex-col bg-base-200 font-sans">
      {/* 네비게이션 바 (Header) */}
      <header className="navbar bg-base-100 shadow-sm sticky top-0 z-50">
        <div className="navbar-start">
          <div className="dropdown">
            <div tabIndex={0} role="button" className="btn btn-ghost lg:hidden">
              <Menu size={24} />
            </div>
            <ul
              tabIndex={0}
              className="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow"
            >
              <li>
                <a>단어장</a>
              </li>
              <li>
                <a>문법 아카이빙</a>
              </li>
            </ul>
          </div>
          <a className="btn btn-ghost text-xl font-bold flex items-center gap-2">
            <BookOpen className="text-primary" size={28} />
            <span className="hidden sm:inline">HKoiKoi's TOEIC Master</span>
            <span className="sm:hidden">TOEIC Master</span>
          </a>
        </div>
        <div className="navbar-center hidden lg:flex">
          <ul className="menu menu-horizontal px-1 text-base">
            <li>
              <a>단어장</a>
            </li>
            <li>
              <a>문법 아카이빙</a>
            </li>
          </ul>
        </div>
        <div className="navbar-end">
          {/* OAuth2 로그인 버튼 자리 */}
          <button className="btn btn-primary">
            <LogIn size={20} />
            로그인
          </button>
        </div>
      </header>

      {/* 메인 컨텐츠 영역 */}
      <main className="flex-1 container mx-auto px-4 py-8">
        {/* 환영 메시지 / Hero Section */}
        <section className="hero bg-base-100 rounded-3xl shadow-sm mb-12 py-12">
          <div className="hero-content text-center">
            <div className="max-w-md">
              <h1 className="text-4xl font-bold">
                목표 점수 달성을 위한
                <br />
                최고의 선택
              </h1>
              <p className="py-6 text-base-content/80">
                직관적이고 깔끔한 UI를 통해 효율적으로 토익 영단어를 암기하고,
                핵심 문법을 학습해보세요.
              </p>
              <button className="btn btn-primary">학습 시작하기</button>
            </div>
          </div>
        </section>

        {/* 주요 기능 카드 섹션 */}
        <section className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* 단어장 카드 */}
          <div className="card bg-base-100 shadow-sm hover:shadow-md transition-shadow">
            <div className="card-body items-center text-center">
              <div className="p-4 bg-primary/10 rounded-full mb-4">
                <BookOpen className="text-primary" size={40} />
              </div>
              <h2 className="card-title text-2xl mb-2">단어장 (Voca)</h2>
              <p className="text-base-content/70">
                플래시카드 형태로 제공되는 직관적인 영단어 리스트입니다.
                <br />
                학습 상태를 체크하며 효율적으로 암기하세요.
              </p>
              <div className="card-actions mt-4 w-full">
                <button className="btn btn-outline btn-primary w-full">
                  단어장 이동
                </button>
              </div>
            </div>
          </div>

          {/* 문법 아카이빙 카드 */}
          <div className="card bg-base-100 shadow-sm hover:shadow-md transition-shadow">
            <div className="card-body items-center text-center">
              <div className="p-4 bg-secondary/10 rounded-full mb-4">
                <BookText className="text-secondary" size={40} />
              </div>
              <h2 className="card-title text-2xl mb-2">문법 아카이빙</h2>
              <p className="text-base-content/70">
                토익 필수 문법 및 핵심 개념을 카테고리별로 모아두었습니다.
                <br />
                쉽고 빠르게 원하는 문법을 찾아보세요.
              </p>
              <div className="card-actions mt-4 w-full">
                <button className="btn btn-outline btn-secondary w-full">
                  문법 학습하기
                </button>
              </div>
            </div>
          </div>
        </section>
      </main>

      {/* 푸터 (Footer) */}
      <footer className="footer footer-center p-4 bg-base-300 text-base-content">
        <aside>
          <p>Copyright © {new Date().getFullYear()} - HKoiKoi's TOEIC Master</p>
        </aside>
      </footer>
    </div>
  );
}

export default App;
