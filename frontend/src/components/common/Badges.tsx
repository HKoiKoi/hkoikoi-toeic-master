// 사용자 권한 배지
interface RoleBadgeProps {
  role: string;
}

export const RoleBadge = ({ role }: RoleBadgeProps) => {
  let badgeStyle = "badge-ghost";

  switch (role.toUpperCase()) {
    case "ADMIN":
      badgeStyle = "badge-error text-white font-bold";
      break;
    case "PRO":
      badgeStyle = "badge-primary font-bold";
      break;
    case "BASIC":
      badgeStyle = "badge-neutral";
      break;
  }

  return <span className={`badge badge-sm ${badgeStyle}`}>{role}</span>;
};

// 소셜 로그인 배지
interface ProviderBadgeProps {
  provider: string;
}

export const ProviderBadge = ({ provider }: ProviderBadgeProps) => {
  let badgeStyle = "badge-outline text-gray-500";

  switch (provider.toUpperCase()) {
    case "GOOGLE":
      badgeStyle = "bg-white text-gray-700 border border-gray-300 font-medium";
      break;
    case "KAKAO":
      badgeStyle = "bg-[#FEE500] text-[#000000] border-transparent font-bold";
      break;
    case "NAVER":
      badgeStyle = "bg-[#03C75A] text-white border-transparent font-bold";
      break;
  }

  return <span className={`badge badge-sm ${badgeStyle}`}>{provider}</span>;
};
