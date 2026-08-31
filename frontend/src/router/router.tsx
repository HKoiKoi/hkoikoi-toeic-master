import { Home } from "@/pages/Home";
import { NotFound } from "@/pages/NotFound";
import { Layout } from "@/components/layout/Layout";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import OAuth2RedirectHandler from "@/pages/OAuth2LoginSuccessHandler";

const Login = () => <div className="text-2xl font-bold">로그인 화면</div>;
const VocaList = () => (
  <div className="text-2xl font-bold">스마트 단어장 화면</div>
);
const GrammarList = () => (
  <div className="text-2xl font-bold">핵심 문법 화면</div>
);
const AdminDashboard = () => (
  <div className="text-2xl font-bold">관리자 대시보드</div>
);

export const AppRouter = () => {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          {/* 기본 라우트 */}
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />

          {/* 핵심 도메인 라우트 */}
          <Route path="/voca" element={<VocaList />} />
          <Route path="/grammar" element={<GrammarList />} />

          {/* 관리자 라우트 */}
          <Route path="/admin" element={<AdminDashboard />} />

          {/* 일치하는 주소가 없을 때 표시할 404 에러 페이지 */}
          <Route path="*" element={<NotFound />} />

          {/* OAuth2 로그인 성공 후 리디렉션 핸들러 */}
          <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
};
