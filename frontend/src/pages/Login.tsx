import { alertUtils } from "@/utils/alertUtils";
import { LogIn } from "lucide-react";

const Login = () => {
  const baseUrl = import.meta.env.VITE_API_BASE_URL || "";

  const handleLogin = (provider: string) => {
    if (provider === "google") {
      window.location.href = `${baseUrl}/oauth2/authorization/${provider}`;

      // TODO: KAKAO, NAVER, GITHUB 로그인 연동
    } else {
      alertUtils.info(
        "준비 중인 기능입니다",
        "현재는 Google 로그인만 지원합니다.",
      );
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-base-200 p-4">
      <div className="card w-full max-w-sm bg-base-100 shadow-xl">
        <div className="card-body items-center text-center rounded-t-lg">
          <div className="bg-primary/10 rounded-full p-4 mb-2">
            <LogIn className="h-8 w-8 text-primary" />
          </div>

          <h2 className="card-title text-2xl font-bold mb-2">
            HKoiKoi's TOEIC Master
          </h2>
          <p className="text-base-content/70 mb-6 text-sm">
            소셜 계정으로 안전하게 로그인하세요.
          </p>

          <div className="flex w-full flex-col gap-3">
            {/* Kakao Login Button */}
            <button
              onClick={() => handleLogin("kakao")}
              className="btn w-full border-none bg-[#FEE500] text-black hover:bg-[#e6cf00]"
            >
              카카오로 시작하기
            </button>

            {/* Naver Login Button */}
            <button
              onClick={() => handleLogin("naver")}
              className="btn w-full border-none bg-[#03C75A] text-white hover:bg-[#02b350]"
            >
              네이버로 시작하기
            </button>

            {/* Google Login Button */}
            <button
              onClick={() => handleLogin("google")}
              className="btn btn-outline w-full"
            >
              Google로 시작하기
            </button>

            {/* GitHub Login Button */}
            <button
              onClick={() => handleLogin("github")}
              className="btn bg-gray-800 text-white hover:bg-gray-900 w-full"
            >
              GitHub로 시작하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
