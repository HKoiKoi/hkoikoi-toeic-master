-- 카테고리 스키마 생성

-- Category 테이블
CREATE TABLE category
(
    category_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID',
    book_id       BIGINT       NOT NULL COMMENT '교재 ID',
    parent_id     BIGINT COMMENT '부모 카테고리 ID',
    name          VARCHAR(100) NOT NULL COMMENT '카테고리명',
    depth         INT          NOT NULL COMMENT '계층 깊이',
    display_order INT          NOT NULL COMMENT '정렬 순서',
    is_deleted    BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '삭제 여부',
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT '카테고리';
