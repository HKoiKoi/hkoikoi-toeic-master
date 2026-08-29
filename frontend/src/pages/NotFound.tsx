import { Home, SearchX } from "lucide-react";

export const NotFound = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-[60vh] px-4 text-center">
      <div className="text-primary/50 mb-6 animate-pulse">
        <SearchX size={80} strokeWidth={1.5} />
      </div>

      {/* 에러 코드 및 제목 */}
      <h1 className="text-6xl font-bold text-base-content mb-4 tracking-tight">
        404
      </h1>
      <h2 className="text-2xl font-semibold text-base-content mb-4">
        페이지를 찾을 수 없습니다.
      </h2>

      {/* 상세 설명 */}
      <p className="text-gray-500 mb-8 max-w-md leading-relaxed">
        입력하신 주소가 잘못되었거나, <br className="lg:hidden" />
        페이지가 삭제 및 이동되어 <br />
        요청하신 페이지를 찾을 수 없습니다.
      </p>

      {/* 홈으로 돌아가기 버튼 */}
      <a href="/" className="btn btn-primary gap-2">
        <Home size={18} />
        홈으로 돌아가기
      </a>
    </div>
  );
};
