import { useState } from "react";
import { Sparkles, ArrowRight } from "lucide-react";
import { PreviewSection } from "@/components/home/PreviewSection";
import { DashboardSection } from "@/components/home/DashboardSection";

export const Home = () => {
  // TODO: Zustand, React Query를 통해 전역 상태로 관리할 유저 정보
  const [isLoggedIn] = useState<boolean>(true);
  const mockMember = {
    nickname: "토익마스터",
    role: "ADMIN",
  };

  return (
    <div className="flex flex-col gap-12 py-4">
      {/* Hero 섹션 */}
      <section className="hero bg-base-200 rounded-3xl p-4 md:p-8">
        <div className="hero-content text-center md:text-left flex-col md:flex-row gap-8 lg:gap-16 w-full justify-between p-0">
          <div className="max-w-3xl">
            {isLoggedIn ? (
              <div className="flex items-center gap-2 justify-center md:justify-start mb-4 text-primary font-bold">
                <Sparkles size={20} />
                <span>환영합니다, {mockMember.nickname}님!</span>
              </div>
            ) : (
              <div className="inline-block bg-primary/10 text-primary font-semibold px-4 py-1.5 rounded-full mb-4 text-sm">
                HKoiKoi's TOEIC Master
              </div>
            )}

            <h1 className="text-4xl md:text-5xl font-extrabold text-base-content leading-tight mb-6">
              효율적인 토익 완성, <br className="block" />
              직관적인 학습 경험
            </h1>
            <p className="text-lg text-gray-400 mb-8 max-w-lg mx-auto md:mx-0 leading-relaxed">
              복잡한 과정 없이 오직 토익 학습에 집중하세요. <br />
              스마트 플래시카드와 체계적인 핵심 문법이
              <br className="block md:hidden" /> 당신의 목표 달성을 돕습니다.
            </p>

            {!isLoggedIn && (
              <a
                href="/login"
                className="btn btn-primary btn-lg gap-2 shadow-lg hover:shadow-primary/50 transition-shadow"
              >
                지금 바로 시작하기 <ArrowRight size={20} />
              </a>
            )}
          </div>
        </div>
      </section>

      {/* 학습 대시보드 / 토익 영단어 맛보기 */}
      {isLoggedIn ? <DashboardSection /> : <PreviewSection />}
    </div>
  );
};
