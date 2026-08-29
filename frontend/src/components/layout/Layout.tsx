import React from "react";
import { Footer } from "@/components/layout/Footer";
import { Header } from "@/components/layout/Header";

interface LayoutProps {
  children: React.ReactNode;
}

export const Layout = ({ children }: LayoutProps) => {
  return (
    <div className="min-h-screen flex flex-col bg-base-100 font-sans">
      {/* 1. 상단 네비게이션 헤더 */}
      <Header />

      {/* 2. 메인 컨텐츠 영역 */}
      <main className="flex-1 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 md:py-12">
        {children}
      </main>

      {/* 3. 하단 푸터 */}
      <Footer />
    </div>
  );
};
