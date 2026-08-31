import Swal, { type SweetAlertOptions } from "sweetalert2";

const baseSwal = Swal.mixin({
  customClass: {
    confirmButton: "btn btn-primary ml-2",
    cancelButton:
      "btn bg-base-200 text-base-content hover:bg-base-300 border-none ml-2",
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
   * 정보/안내 알림창
   */
  info: (title: string, text?: string, options?: SweetAlertOptions) => {
    return baseSwal.fire({
      icon: "info",
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

  /**
   * 텍스트 입력을 받는 프롬프트 알림창
   * @returns 사용자가 입력한 값 (취소 누르면 undefined 반환)
   */
  prompt: async (
    title: string,
    inputLabel: string,
    inputValue?: string,
    inputValidator?: (value: string) => string | undefined | void,
    confirmButtonText = "확인",
    cancelButtonText = "취소",
    options?: SweetAlertOptions,
  ): Promise<string | undefined> => {
    const result = await baseSwal.fire({
      title,
      input: "text",
      inputLabel,
      inputValue,
      showCancelButton: true,
      confirmButtonText,
      cancelButtonText,
      inputValidator,
      ...options,
    } as SweetAlertOptions);

    return result.value;
  },
};
