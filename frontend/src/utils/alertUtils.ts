import Swal, { type SweetAlertOptions } from "sweetalert2";

const baseSwal = Swal.mixin({
  customClass: {
    confirmButton: "btn btn-primary ml-2",
    cancelButton: "btn btn-ghost border border-gray-300",
    popup: "rounded-2xl",
  },
  buttonsStyling: false,
});

export const alertUtils = {
  /**
   * 성공 알림창
   */
  success: (title: string, text?: string, options?: SweetAlertOptions) => {
    return baseSwal.fire({
      icon: "success",
      title,
      text,
      confirmButtonText: "확인",
      ...options,
    });
  },

  /**
   * 에러 알림창
   */
  error: (title: string, text?: string, options?: SweetAlertOptions) => {
    return baseSwal.fire({
      icon: "error",
      title,
      text,
      confirmButtonText: "확인",
      ...options,
    });
  },

  /**
   * 경고 알림창
   */
  warning: (title: string, text?: string, options?: SweetAlertOptions) => {
    return baseSwal.fire({
      icon: "warning",
      title,
      text,
      confirmButtonText: "확인",
      ...options,
    });
  },

  /**
   * 확인/취소 알림창
   * @returns 사용자가 '확인'을 누르면 true를 반환하는 Promise
   */
  confirm: async (
    title: string,
    text?: string,
    confirmButtonText = "확인",
    cancelButtonText = "취소",
    options?: SweetAlertOptions,
  ): Promise<boolean> => {
    const result = await baseSwal.fire({
      icon: "question",
      title,
      text,
      showCancelButton: true,
      confirmButtonText,
      cancelButtonText,
      ...options,
    });

    return result.isConfirmed;
  },
};
