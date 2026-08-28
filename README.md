# HKoiKoi's TOEIC Master

직관적이고 깔끔한 UI를 제공하여 사용자가 효율적으로 토익 영단어를 암기하고, 핵심 문법을 학습할 수 있는 반응형 웹 애플리케이션입니다.

## Tech Stack

### Backend

- **Java 25**
- **Spring Boot 4.1.1**
- Spring Security
- OAuth2 Client
- JWT

### Frontend

- **React 19 (TypeScript)**
- **Vite 8**
- Tailwind CSS V4
- Daisy UI 5
- Pretendard (Typography)
- SweetAlert2
- Axios
- lucide-react

### 인프라 및 배포 (Infra)

- GitHub Private Repository (`hkoikoi-toeic-master`)
- GitHub Actions (CI/CD 및 Runner 활용)

---

## Project Structure

본 프로젝트는 하나의 레포지토리 내에서 백엔드와 프론트엔드 디렉터리를 분리하여 관리하는 **Monorepo** 형태를 취하고 있습니다.

```text
hkoikoi-toeic-master/
├── backend/
├── frontend/
└── .github/
    └── workflows/
```

---

## 사용자 및 권한 정책

보안을 위해 일반 폼 로그인은 지원하지 않으며, OAuth2로만 로그인할 수 있습니다.

| 권한                    | 설명                                                                                 |
| ----------------------- | ------------------------------------------------------------------------------------ |
| **ADMIN (관리자)**      | 시스템 내 모든 데이터(단어, 문법 등) 작성, 수정, 삭제 및 사용자 권한 관리            |
| **PRO (심화 사용자)**   | 관리자가 직접 작성한 보카, 입문 기출 문법 등 모든 심화 학습 콘텐츠 열람 및 학습 가능 |
| **BASIC (기본 사용자)** | 제한된 무료 학습 권한 (기본 토익 영단어 10개만 열람 및 학습 가능)                    |

---

## 주요 기능

1. **안전한 인증 및 인가**
   - Spring Security와 JWT를 활용한 OAuth2 소셜 로그인 구현
2. **스마트 단어장**
   - 영단어 리스트 제공, 플래시카드 형태 등 직관적인 UI 제공
   - 사용자가 단어를 완벽히 외웠는지 암기/미암기 상태를 직관적으로 체크 가능
3. **토익 문법 아카이빙**
   - 토익 필수 문법 및 핵심 개념을 쉽게 찾아볼 수 있도록 카테고리화
   - 게시판 또는 위키 형태의 깔끔한 UI 제공
4. **반응형 UI**
   - 모바일, 태블릿, 데스크톱 등 다양한 디바이스 환경에서 깨짐 없이 쾌적하게 동작
   - Tailwind CSS와 Daisy UI 활용
