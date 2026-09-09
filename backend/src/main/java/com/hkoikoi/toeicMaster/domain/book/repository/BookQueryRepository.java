package com.hkoikoi.toeicMaster.domain.book.repository;

import static com.hkoikoi.toeicMaster.domain.book.entity.QBook.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hkoikoi.toeicMaster.domain.book.dto.BookSearchCondition;
import com.hkoikoi.toeicMaster.domain.book.dto.BookSearchResponse;
import com.hkoikoi.toeicMaster.domain.book.enums.BookType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<BookSearchResponse> searchBooks(BookSearchCondition condition, long offset, int limit) {

		List<Long> ids = findIds(condition, offset, limit);

		if (ids.isEmpty()) {
			return Collections.emptyList();
		}

		return jpaQueryFactory
			.select(Projections.constructor(BookSearchResponse.class,
				book.id,
				book.title,
				book.publisher,
				book.bookType,
				book.isActive,
				book.createdAt
			))
			.from(book)
			.where(book.id.in(ids))
			.orderBy(book.id.desc())
			.fetch();
	}

	public Long countBooks(BookSearchCondition condition) {

		List<Long> ids = jpaQueryFactory
			.select(book.id)
			.from(book)
			.where(
				titleContains(condition.title()),
				bookTypeEq(condition.bookType()),
				isActiveEq(condition.isActive()),
				createdAtBetween(condition.startDate(), condition.endDate()),
				book.isDeleted.isFalse()
			)
			.fetch();

		return (long)ids.size();
	}

	private List<Long> findIds(BookSearchCondition condition, long offset, int limit) {
		return jpaQueryFactory
			.select(book.id)
			.from(book)
			.where(
				titleContains(condition.title()),
				bookTypeEq(condition.bookType()),
				isActiveEq(condition.isActive()),
				createdAtBetween(condition.startDate(), condition.endDate()),
				book.isDeleted.isFalse()
			)
			.orderBy(book.id.desc())
			.offset(offset)
			.limit(limit)
			.fetch();
	}

	private BooleanExpression titleContains(String title) {
		return title != null && !title.isBlank() ? book.title.containsIgnoreCase(title) : null;
	}

	private BooleanExpression bookTypeEq(BookType bookType) {
		return bookType != null ? book.bookType.eq(bookType) : null;
	}

	private BooleanExpression isActiveEq(Boolean isActive) {
		return isActive != null ? book.isActive.eq(isActive) : null;
	}

	private BooleanExpression createdAtBetween(LocalDate startDate, LocalDate endDate) {

		if (startDate == null && endDate == null) {
			return null;
		}

		if (startDate != null && endDate != null) {
			return book.createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
		}

		if (startDate != null) {
			return book.createdAt.goe(startDate.atStartOfDay());
		}

		return book.createdAt.loe(endDate.atTime(23, 59, 59));
	}
}
