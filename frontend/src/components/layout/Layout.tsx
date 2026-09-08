import { Outlet } from "react-router-dom";
import { Header } from "@/components/layout/Header";
import { Footer } from "@/components/layout/Footer";

export const Layout = () => {
  return (
    <div className="min-h-screen flex flex-col bg-base-100 font-sans">
      {/* 상단 네비게이션 헤더 */}
      <Header />

      {/* 메인 컨텐츠 영역 */}
      <main className="flex-1 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 md:py-12">
        <Outlet />
      </main>

      {/* 하단 푸터 */}
      <Footer />
    </div>
  );
};
