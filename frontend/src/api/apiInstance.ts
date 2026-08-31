import { useAuthStore } from "@/store/authStore";
import type { ApiResponse } from "@/types/api";
import type { TokenRefreshResponse } from "@/types/auth";
import { alertUtils } from "@/utils/alertUtils";
import axios, { isAxiosError, type InternalAxiosRequestConfig } from "axios";

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 5000,
});

// 동시 갱신 방지를 위한 Queue 변수
let isRefreshing = false;
let refreshSubscribers: ((token: string) => void)[] = [];

const addRefreshSubscriber = (callback: (token: string) => void) => {
  refreshSubscribers.push(callback);
};

const onTokenRefreshed = (token: string) => {
  refreshSubscribers.forEach((callback) => callback(token));
  refreshSubscribers = [];
};

// 요청 인터셉터
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const { accessToken } = useAuthStore.getState();

    if (accessToken && config.headers) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 응답 인터셉터
api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config as InternalAxiosRequestConfig & {
      _retry?: boolean;
    };

    if (isAxiosError<ApiResponse<unknown>>(error)) {
      const status = error.response?.status;

      // 401 Unauthorized 에러 처리
      if (status === 401 && originalRequest && !originalRequest._retry) {
        originalRequest._retry = true;

        if (!isRefreshing) {
          isRefreshing = true;

          try {
            const response = await axios.post<
              ApiResponse<TokenRefreshResponse>
            >(
              `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/refresh`,
              {},
              { withCredentials: true },
            );

            const newAccessToken = response.data.data?.accessToken;

            if (!newAccessToken) {
              throw new Error("재발급된 Access Token이 없습니다.");
            }

            const { member, setAuth } = useAuthStore.getState();

            if (member) {
              setAuth(newAccessToken, member);
            }

            onTokenRefreshed(newAccessToken);

            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            return api(originalRequest);
          } catch (reissueError) {
            useAuthStore.getState().clearAuth();
            refreshSubscribers = [];

            alertUtils
              .error("인증 오류", "세션이 만료되었습니다. 다시 로그인해주세요.")
              .then(() => {
                window.location.href = "/login";
              });

            return Promise.reject(reissueError);
          } finally {
            isRefreshing = false;
          }
        }

        // 갱신 중일 때 들어온 다른 요청들은 Queue에 보관
        return new Promise((resolve) => {
          addRefreshSubscriber((token: string) => {
            if (originalRequest.headers) {
              originalRequest.headers.Authorization = `Bearer ${token}`;
            }

            resolve(api(originalRequest));
          });
        });
      }

      // 403 Forbidden 에러 처리
      if (status === 403) {
        alertUtils.error("권한 오류", "해당 작업을 수행할 권한이 없습니다.");
      }

      // 500+ 서버 에러 처리
      if (status && status >= 500) {
        alertUtils.error(
          "서버 오류",
          "서버에서 문제가 발생했습니다. 잠시 후 다시 시도해주세요.",
        );
      }

      // 공통 에러 메시지 추출
      const errorResponse = error.response?.data?.error;
      if (errorResponse) {
        return Promise.reject(errorResponse);
      }
    }

    return Promise.reject(error);
  },
);
