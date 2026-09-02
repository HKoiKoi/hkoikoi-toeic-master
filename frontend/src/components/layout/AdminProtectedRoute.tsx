import { useEffect } from "react";
import { alertUtils } from "@/utils/alertUtils";
import { useAuthStore } from "@/store/authStore";
import { Navigate, Outlet } from "react-router-dom";

export const AdminProtectedRoute = () => {
  const { isAuthenticated, member } = useAuthStore();

  useEffect(() => {
    // 권한 검증
    if (!isAuthenticated || member?.role !== "ADMIN") {
      alertUtils.error("접근 거부", "관리자 권한이 필요합니다.");
    }
  }, [isAuthenticated, member]);

  // 권한이 없는 경우 메인 페이지로 리다이렉트
  if (!isAuthenticated || member?.role !== "ADMIN") {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};
