import { ExternalLink } from "lucide-react";

export const Footer = () => {
  return (
    <footer className="bg-base-200 text-base-content border-t border-base-300 mt-auto">
      {/* | 소개 | 메뉴 | 외부링크 | */}
      <div className="max-w-7xl mx-auto p-10 flex flex-col md:flex-row md:justify-between gap-10">
        {/* 1. 소개 */}
        <aside className="max-w-xs flex flex-col gap-2">
          <h2 className="text-xl font-bold mb-2">HKoiKoi's TOEIC Master</h2>
          <p className="text-sm text-gray-500 leading-relaxed">
            직관적인 UI로 효율적인 토익 영단어 암기와
            <br />
            핵심 문법 학습을 지원하는 반응형 웹 앱입니다.
          </p>
        </aside>

        {/* 2. 메뉴 */}
        <nav className="flex flex-col gap-2">
          <h6 className="footer-title opacity-60 font-bold uppercase tracking-wider mb-2">
            메뉴
          </h6>
          <a href="/voca" className="link link-hover text-sm">
            단어장
          </a>
          <a href="/grammar" className="link link-hover text-sm">
            핵심 문법
          </a>
        </nav>

        {/* 3. 외부링크 */}
        <nav className="flex flex-col gap-2">
          <h6 className="footer-title opacity-60 font-bold uppercase tracking-wider mb-2">
            외부링크
          </h6>
          {/* 토익 공식 접수처 링크 */}
          <a
            href="https://exam.toeic.co.kr/receipt/receiptStep1.php"
            target="_blank"
            rel="noopener noreferrer"
            className="link link-hover flex items-center gap-1 text-sm"
          >
            토익 시험 신청 <ExternalLink size={14} className="opacity-70" />
          </a>

          {/* GitHub 저장소 링크 */}
          <a
            href="https://github.com/hkoikoi/hkoikoi-toeic-master"
            target="_blank"
            rel="noopener noreferrer"
            className="link link-hover flex items-center gap-2 text-sm"
          >
            GitHub 저장소
            {/* lucide-react 대신 직접 SVG 아이콘 삽입 */}
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="14"
              height="14"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="opacity-70"
            >
              <path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4" />
              <path d="M9 18c-4.51 2-5-2-7-2" />
            </svg>
          </a>
        </nav>
      </div>

      {/* 저작권 */}
      <div className="footer footer-center p-4 border-t border-base-300 text-xs text-gray-500">
        <aside>
          <p>© {new Date().getFullYear()} HKoiKoi. All rights reserved.</p>
        </aside>
      </div>
    </footer>
  );
};
