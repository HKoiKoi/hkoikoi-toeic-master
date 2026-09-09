-- Category 테이블 인덱스 추가 (조회 성능 최적화)

CREATE INDEX idx_category_book_id ON category (book_id);
CREATE INDEX idx_category_parent_id ON category (parent_id);
