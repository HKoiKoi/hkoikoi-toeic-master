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
  const movablePageCount = 10;

  const currentBlock = Math.floor((currentPage - 1) / movablePageCount);
  const startPage = currentBlock * movablePageCount + 1;
  const maxItemsInBlock = (startPage + movablePageCount - 1) * pageSize;

  const hasNext = totalCount > maxItemsInBlock;
  const hasPrev = startPage > 1;

  let endPage = startPage + movablePageCount - 1;
  if (!hasNext) {
    const calculatedEndPage = Math.ceil(totalCount / pageSize);
    endPage = calculatedEndPage === 0 ? 1 : calculatedEndPage;
  }

  const pages = Array.from(
    { length: endPage - startPage + 1 },
    (_, i) => startPage + i,
  );

  return (
    <div className="join">
      {/* 이전 블록 이동 버튼 */}
      <button
        className="join-item btn btn-sm"
        disabled={!hasPrev}
        // 이전 블록의 마지막 페이지로 이동
        onClick={() => onPageChange(startPage - 1)}
      >
        «
      </button>

      {/* 페이지 번호 */}
      {pages.map((page) => (
        <button
          key={page}
          className={`join-item btn btn-sm ${
            currentPage === page ? "btn-active pointer-events-none" : ""
          }`}
          onClick={() => onPageChange(page)}
        >
          {page}
        </button>
      ))}

      {/* 다음 블록 이동 버튼 */}
      <button
        className="join-item btn btn-sm"
        disabled={!hasNext}
        // 다음 블록의 첫 페이지로 이동
        onClick={() => onPageChange(endPage + 1)}
      >
        »
      </button>
    </div>
  );
};
