import { useState, useEffect } from "react";

interface PaginationProps {
  currentPage: number;
  pageSize: number;
  totalCount: number;
  onPageChange: (page: number) => void;
}

export const Pagination = ({
  currentPage,
  pageSize,
  totalCount,
  onPageChange,
}: PaginationProps) => {
  // 화면 크기 감지 (768px 미만이면 모바일로 간주)
  const [isMobile, setIsMobile] = useState(() => window.innerWidth < 768);

  useEffect(() => {
    // 리사이즈 이벤트 핸들러
    const handleResize = () => {
      setIsMobile(window.innerWidth < 768);
    };

    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  // 모바일/데스크탑에 따라 보여줄 페이지 버튼 수 결정
  const maxButtons = isMobile ? 5 : 10;
  const threshold = isMobile ? 3 : 5;
  const beforeCount = isMobile ? 2 : 4;

  const totalPages = Math.ceil(totalCount / pageSize) || 1;

  let startPage = 1;
  let endPage = totalPages;

  let showPrevControls = false;
  let showNextControls = false;

  // 전체 페이지가 maxButtons보다 클 때만 슬라이딩 적용
  if (totalPages > maxButtons) {
    if (currentPage <= threshold) {
      // 시작 구간 (모바일: 1~3, 데스크탑: 1~5)
      startPage = 1;
      endPage = maxButtons;
      showPrevControls = false;
      showNextControls = true;
    } else {
      // 중간 및 끝 구간
      showPrevControls = true;

      // 현재 페이지를 기준으로 윈도우 이동
      startPage = currentPage - beforeCount;
      endPage = startPage + (maxButtons - 1);

      if (endPage >= totalPages) {
        endPage = totalPages;
        startPage = endPage - (maxButtons - 1);
        showNextControls = false;
      } else {
        showNextControls = true;
      }
    }
  }

  const pages = Array.from(
    { length: endPage - startPage + 1 },
    (_, i) => startPage + i,
  );

  return (
    <div className="join">
      {/* <, << 컨트롤 그룹 */}
      {showPrevControls && (
        <>
          <button
            className="btn join-item btn-sm px-2 sm:px-3"
            onClick={() => onPageChange(1)}
          >
            &laquo;
          </button>
          <button
            className="join-item btn btn-sm px-2 sm:px-3"
            onClick={() => onPageChange(Math.max(1, currentPage - maxButtons))}
          >
            &lsaquo;
          </button>
        </>
      )}

      {/* 페이지 버튼 */}
      {pages.map((page) => {
        const isActive = currentPage === page;
        return (
          <button
            key={page}
            className={`join-item btn btn-sm w-8 sm:w-10 ${
              isActive
                ? "btn-primary pointer-events-none"
                : "btn-ghost border-base-200 hover:bg-base-200"
            }`}
            onClick={() => onPageChange(page)}
          >
            {page}
          </button>
        );
      })}

      {/* >, >> 컨트롤 그룹 */}
      {showNextControls && (
        <>
          <button
            className="join-item btn btn-sm px-2 sm:px-3"
            onClick={() =>
              onPageChange(Math.min(totalPages, currentPage + maxButtons))
            }
          >
            &rsaquo;
          </button>
          <button
            className="join-item btn btn-sm px-2 sm:px-3"
            onClick={() => onPageChange(totalPages)}
          >
            &raquo;
          </button>
        </>
      )}
    </div>
  );
};
