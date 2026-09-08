import { useEffect, useRef } from "react";
import { memberApi } from "@/api/memberApi";
import { alertUtils } from "@/utils/alertUtils";
import { useAuthStore } from "@/store/authStore";
import { useNavigate, useSearchParams } from "react-router-dom";

export const useAuthFlow = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const setAuth = useAuthStore((state) => state.setAuth);

  const isProcessing = useRef<boolean>(false);

  useEffect(() => {
    const processLogin = async () => {
      if (isProcessing.current) return;
      isProcessing.current = true;

      const accessToken = searchParams.get("accessToken");
      const error = searchParams.get("error");

      // 백엔드에서 OAuth2 로그인 실패 시
      if (error) {
        await alertUtils.error(
          "로그인 실패",
          "OAuth2 로그인 과정에서 오류가 발생했습니다. 다시 시도해주세요.",
        );
        navigate("/login", { replace: true });
        return;
      }

      // 토큰이 없는 경우
      if (!accessToken) {
        navigate("/login", { replace: true });
        return;
      }

      try {
        useAuthStore.setState({ accessToken });

        const response = await memberApi.getMyInfo();
        const memberInfo = response.data;

        if (!memberInfo) {
          throw new Error("회원 정보를 가져오지 못했습니다.");
        }

        setAuth(accessToken, memberInfo);

        await alertUtils.success(
          "로그인 성공",
          `환영합니다, ${memberInfo.nickname}님!`,
        );
        navigate("/", { replace: true });
      } catch (err) {
        console.error("로그인 처리 중 오류 발생:", err);

        useAuthStore.getState().clearAuth();
        await alertUtils.error(
          "로그인 실패",
          "회원 정보를 가져오는 중 오류가 발생했습니다. 다시 시도해주세요.",
        );
        navigate("/login", { replace: true });
      }
    };

    processLogin();
  }, [searchParams, navigate, setAuth]);
};
