import { useAuthFlow } from "@/hooks/useAuthFlow";

const OAuth2RedirectHandler = () => {
  useAuthFlow();

  return (
    <div className="flex flex-col items-center h-screen bg-base-100 w-full justify-center gap-4">
      <span className="text-primary loading loading-spinner loading-lg"></span>
      <p className="text-base-content/70 text-lg font-medium">
        안전하게 로그인 처리 중입니다...
      </p>
    </div>
  );
};

export default OAuth2RedirectHandler;
