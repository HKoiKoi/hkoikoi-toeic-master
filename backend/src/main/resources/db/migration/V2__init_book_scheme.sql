-- 교재 스키마 생성

-- Book 테이블
CREATE TABLE book
(
    book_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '교재 ID',
    title      VARCHAR(100) NOT NULL COMMENT '교재명',
    publisher  VARCHAR(50) COMMENT '출판사',
    book_type  VARCHAR(20)  NOT NULL COMMENT '교재 유형',
    is_active  BOOLEAN      NOT NULL DEFAULT TRUE COMMENT '서비스 노출 여부',
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '삭제 여부',
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT '교재';
