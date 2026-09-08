-- 사용자 스키마 생성

-- Member 테이블
CREATE TABLE member
(
    member_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 ID',
    email       VARCHAR(255) NOT NULL COMMENT '이메일',
    nickname    VARCHAR(10)  NOT NULL COMMENT '닉네임',
    provider    VARCHAR(20)  NOT NULL COMMENT 'OAuth2 제공자',
    provider_id VARCHAR(255) NOT NULL COMMENT 'OAuth2 제공자 ID',
    role        VARCHAR(20)  NOT NULL DEFAULT 'BASIC' COMMENT '권한',
    is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '삭제 여부',
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',

    UNIQUE KEY uk_member_nickname (nickname),
    UNIQUE KEY uk_member_provider_provider_id (provider, provider_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    COMMENT '사용자';
