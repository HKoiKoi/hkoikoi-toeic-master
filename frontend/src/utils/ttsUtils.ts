import { alertUtils } from "@/utils/alertUtils";

export const ttsUtils = {
  /**
   * 영단어를 발음으로 읽어주는 함수
   * @param word 읽을 단어 또는 문장
   * @param rate 재생 속도 (기본값: 1.0, 0.1 ~ 10 사이 조절 가능)
   */
  speakWord: (word: string, rate: number = 1.0) => {
    if (!window.speechSynthesis) {
      alertUtils.error(
        "지원 불가",
        "음성 재생을 지원하지 않는 브라우저입니다.",
      );
      return;
    }

    window.speechSynthesis.cancel();

    const utterance = new SpeechSynthesisUtterance(word);
    utterance.lang = "en-US";
    utterance.rate = rate;

    const playWithEnglishVoice = () => {
      const voices = window.speechSynthesis.getVoices();

      const englishVoices = voices.filter((voice) =>
        voice.lang.toLowerCase().startsWith("en"),
      );

      let selectedVoice = englishVoices.find(
        (voice) =>
          voice.name.includes("Google US English") ||
          voice.name.includes("Samantha") ||
          voice.name.includes("Alex") ||
          voice.name.includes("Zira") ||
          voice.name.includes("David"),
      );

      if (!selectedVoice) {
        selectedVoice = englishVoices.find(
          (voice) => voice.lang === "en-US" || voice.lang === "en_US",
        );
      }

      if (selectedVoice) {
        utterance.voice = selectedVoice;
      }

      window.speechSynthesis.speak(utterance);
    };

    if (window.speechSynthesis.getVoices().length === 0) {
      const handleVoicesChanged = () => {
        playWithEnglishVoice();

        window.speechSynthesis.removeEventListener(
          "voiceschanged",
          handleVoicesChanged,
        );
      };

      window.speechSynthesis.addEventListener(
        "voiceschanged",
        handleVoicesChanged,
      );
    } else {
      playWithEnglishVoice();
    }
  },

  /**
   * 현재 재생 중인 음성 즉지 정지하는 함수
   */
  stop: () => {
    if (window.speechSynthesis) {
      window.speechSynthesis.cancel();
    }
  },
};
