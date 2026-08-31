import { BookOpen, BookText, CheckCircle2 } from "lucide-react";

export const DashboardSection = () => {
  return (
    <section className="flex flex-col gap-6">
      <div className="flex items-center justify-between px-2">
        <h2 className="text-2xl font-bold text-base-content">학습 대시보드</h2>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* 스마트 단어장 */}
        <div className="card bg-base-100 border border-base-200 shadow-sm hover:shadow-md transition-shadow">
          <div className="card-body gap-4">
            <div className="w-12 h-12 rounded-2xl bg-primary/10 flex items-center justify-center text-primary mb-2">
              <BookOpen size={24} />
            </div>
            <h3 className="card-title text-xl">스마트 단어장</h3>
            <p className="text-gray-400 text-sm">
              TTS 원어민 발음 듣기와 플래시카드 형태의 UI로 <br />
              영단어 암기 상태를 직관적으로 체크하고 마스터하세요.
            </p>
            <ul className="text-sm text-gray-400 flex flex-col gap-2 mt-2">
              <li className="flex items-center gap-2">
                <CheckCircle2 size={16} className="text-success" />
                암기 / 미암기 상태 분리
              </li>
              <li className="flex items-center gap-2">
                <CheckCircle2 size={16} className="text-success" />
                클릭 한 번으로 발음 재생
              </li>
            </ul>
            <div className="card-actions justify-end mt-4">
              <a
                href="/voca"
                className="btn btn-primary btn-sm md:btn-md w-full sm:w-auto"
              >
                단어장 학습하기
              </a>
            </div>
          </div>
        </div>

        {/* 핵심 문법 */}
        <div className="card bg-base-100 border border-base-200 shadow-sm hover:shadow-md transition-shadow">
          <div className="card-body gap-4">
            <div className="w-12 h-12 rounded-2xl bg-secondary/10 flex items-center justify-center text-secondary mb-2">
              <BookText size={24} />
            </div>
            <h3 className="card-title text-xl">핵심 문법</h3>
            <p className="text-gray-400 text-sm">
              토익 필수 문법과 핵심 개념을 카테고리별로 깔끔하게 <br />
              찾아볼 수 있는 위키 형태의 게시판입니다.
            </p>
            <ul className="text-sm text-gray-400 flex flex-col gap-2 mt-2">
              <li className="flex items-center gap-2">
                <CheckCircle2 size={16} className="text-success" />
                파트 5, 6 핵심 문법 요약
              </li>
              <li className="flex items-center gap-2">
                <CheckCircle2 size={16} className="text-success" />
                기출 변형 예문 제공
              </li>
            </ul>
            <div className="card-actions justify-end mt-4">
              <a
                href="/grammar"
                className="btn btn-secondary btn-outline btn-sm md:btn-md w-full sm:w-auto"
              >
                문법 학습하기
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
