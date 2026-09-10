package com.hkoikoi.toeicMaster.domain.category.repository;

import static com.hkoikoi.toeicMaster.domain.category.entity.QCategory.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public void deleteAllById(List<Long> ids) {
		jpaQueryFactory
			.update(category)
			.set(category.isDeleted, true)
			.where(category.id.in(ids))
			.execute();
	}

	public void deleteAllByBookId(Long bookId) {
		jpaQueryFactory
			.update(category)
			.set(category.isDeleted, true)
			.where(category.book.id.eq(bookId))
			.execute();
	}
}
