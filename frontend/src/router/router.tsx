import Login from "@/pages/Login";
import { Home } from "@/pages/Home";
import { NotFound } from "@/pages/NotFound";
import { Layout } from "@/components/layout/Layout";
import { AdminHome } from "@/pages/admin/AdminHome";
import { AdminLayout } from "@/components/layout/AdminLayout";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import OAuth2RedirectHandler from "@/pages/OAuth2LoginSuccessHandler";
import { AdminProtectedRoute } from "@/components/layout/AdminProtectedRoute";

const VocaList = () => (
  <div className="text-2xl font-bold">스마트 단어장 화면</div>
);
const GrammarList = () => (
  <div className="text-2xl font-bold">핵심 문법 화면</div>
);

export const AppRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        {/* 일반 사용자용 서비스 라우트 */}
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />

          <Route path="/voca" element={<VocaList />} />
          <Route path="/grammar" element={<GrammarList />} />

          <Route path="*" element={<NotFound />} />

          <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
        </Route>

        {/* 관리자 라우트 */}
        <Route element={<AdminProtectedRoute />}>
          <Route path="/admin" element={<AdminLayout />}>
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
