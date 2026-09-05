import Login from "@/pages/Login";
import { Home } from "@/pages/Home";
import { NotFound } from "@/pages/NotFound";
import { Layout } from "@/components/layout/Layout";
import { Route, Routes, BrowserRouter, Outlet } from "react-router-dom";
import OAuth2RedirectHandler from "@/pages/OAuth2LoginSuccessHandler";
import { AdminProtectedRoute } from "@/components/layout/AdminProtectedRoute";
import { AdminDashboard } from "@/pages/admin/AdminDashboard";

const VocaList = () => (
  <div className="text-2xl font-bold">스마트 단어장 화면</div>
);
const GrammarList = () => (
  <div className="text-2xl font-bold">핵심 문법 화면</div>
);
const AdminHome = () => (
  <div className="text-2xl font-bold">관리자 대시보드 메인</div>
);

const PublicLayout = () => {
  return (
    <Layout>
      <Outlet />
    </Layout>
  );
};

export const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        {/* 일반 사용자용 서비스 라우트 */}
        <Route element={<PublicLayout />}>
          {/* 기본 라우트 */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />

          {/* 핵심 도메인 라우트 */}
          <Route path="/voca" element={<VocaList />} />
          <Route path="/grammar" element={<GrammarList />} />

          {/* 일치하는 주소가 없을 때 표시할 404 에러 페이지 */}
          <Route path="*" element={<NotFound />} />

          {/* OAuth2 로그인 성공 후 리디렉션 핸들러 */}
          <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
        </Route>

        {/* 관리자 라우트 */}
        <Route element={<AdminProtectedRoute />}>
          <Route path="/admin" element={<AdminDashboard />}>
            <Route index element={<AdminHome />} />
            <Route
              path="members"
              element={<div>회원 관리 화면 (여기에 MemberPage 연결)</div>}
            />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
