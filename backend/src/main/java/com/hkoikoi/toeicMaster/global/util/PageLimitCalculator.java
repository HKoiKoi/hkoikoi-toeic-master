package com.hkoikoi.toeicMaster.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageLimitCalculator {

	/**
	 * Bounded Count 최적화 한계값 계산
	 * 사용자가 볼 수 있는 페이징 블록 범위까지만 데이터 개수를 세도록 제한값 설정
	 *
	 * @param page 현재 페이지 번호 (1부터 시작)
	 * @param pageSize 페이지당 표시할 데이터 개수
	 * @param movablePageCount 프론트엔드 UI에 노출할 최대 페이지 블록 수 (예: |1|2|...|9|10|)
	 * @return 다음 페이지 블록의 존재 여부를 파악할 수 있는 제한된 총 카운트 수
	 */
	public static Long calculatePageLimit(Long page, Long pageSize, Long movablePageCount) {
		return (((page - 1) / movablePageCount) + 1) * pageSize * movablePageCount + 1;
	}
}
