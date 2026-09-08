import { useState } from "react";
import { Volume2 } from "lucide-react";
import { ttsUtils } from "@/utils/ttsUtils";

// 토익 핵심 단어 16개 목업 데이터
const MOCK_WORDS = [
  { word: "accommodate", meaning: "수용하다, 공간을 제공하다" },
  { word: "fluctuate", meaning: "변동하다, 오르내리다" },
  { word: "implement", meaning: "실행하다, 이행하다" },
  { word: "mandatory", meaning: "의무적인, 필수의" },
  { word: "subsequent", meaning: "그 다음의, 차후의" },
  { word: "alleviate", meaning: "완화하다, 경감하다" },
  { word: "compile", meaning: "편집하다, 엮다" },
  { word: "endorse", meaning: "지지하다, 보증하다" },
  { word: "lucrative", meaning: "수익성이 좋은" },
  { word: "preliminary", meaning: "예비의, 임시의" },
  { word: "reimburse", meaning: "환급하다, 배상하다" },
  { word: "scrutinize", meaning: "면밀히 조사하다" },
  { word: "tangible", meaning: "유형의, 실체가 있는" },
  { word: "versatile", meaning: "다재다능한, 다용도의" },
  { word: "comprehensive", meaning: "포괄적인, 종합적인" },
  { word: "allocate", meaning: "할당하다, 배분하다" },
];

export const PreviewSection = () => {
  const [randomWords] = useState(() => {
    return [...MOCK_WORDS].sort(() => 0.5 - Math.random()).slice(0, 4);
  });

  return (
    <section className="flex flex-col gap-6">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between px-2 gap-4">
        <div>
          <h2 className="text-2xl font-bold text-base-content mb-1">
            오늘의 토익 영단어 맛보기
          </h2>
          <p className="text-sm text-gray-400">
            로그인하시면 더 많은 단어를 체계적으로 학습할 수 있습니다.
          </p>
        </div>
        <a href="/login" className="btn btn-outline btn-primary btn-sm">
          전체 단어장 보기
        </a>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        {randomWords.map((item, index) => (
          <div
            key={index}
            className="card bg-base-100 border border-base-200 shadow-sm"
          >
            <div className="card-body p-5 flex flex-col items-center text-center gap-3">
              <h3 className="text-xl font-bold text-base-content">
                {item.word}
              </h3>
              <p className="text-gray-500 text-sm font-medium">
                {item.meaning}
              </p>
              <button
                onClick={() => ttsUtils.speakWord(item.word)}
                className="btn btn-circle btn-ghost btn-sm text-gray-400 hover:text-primary mt-2"
                title="발음 듣기"
              >
                <Volume2 size={18} />
              </button>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};
