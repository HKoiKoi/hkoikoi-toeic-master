import Login from "@/pages/Login";
import { Home } from "@/pages/Home";
import { NotFound } from "@/pages/NotFound";
import { Layout } from "@/components/layout/Layout";
import { AdminHome } from "@/pages/admin/AdminHome";
import { AdminMembers } from "@/pages/admin/AdminMembers";
import { AdminLayout } from "@/components/layout/AdminLayout";
import OAuth2RedirectHandler from "@/pages/OAuth2LoginSuccessHandler";
import { Route, Routes, BrowserRouter, useParams } from "react-router-dom";
import { AdminProtectedRoute } from "@/components/layout/AdminProtectedRoute";

const VocaList = () => (
  <div className="text-2xl font-bold">스마트 단어장 화면</div>
);
const GrammarList = () => (
  <div className="text-2xl font-bold">핵심 문법 화면</div>
);
const AdminBooks = () => (
  <div className="p-8">
    <h1 className="text-3xl font-bold">교재/목차 관리</h1>
  </div>
);
const AdminCategories = () => {
  const { bookId } = useParams();

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold">목차 관리 (교재 ID: {bookId})</h1>
      <p className="mt-4">
        여기에 트리 컴포넌트와 DnD 기능이 들어갈 예정입니다.
      </p>
    </div>
  );
};

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
            <Route path="/admin/members" element={<AdminMembers />} />
            <Route path="/admin/books" element={<AdminBooks />} />
            <Route
              path="/admin/books/:bookId/categories"
              element={<AdminCategories />}
            />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
