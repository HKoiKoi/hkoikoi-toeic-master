package com.hkoikoi.toeicMaster.domain.member.repository;

import static com.hkoikoi.toeicMaster.domain.member.entity.QMember.*;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hkoikoi.toeicMaster.domain.member.dto.MemberSearchCondition;
import com.hkoikoi.toeicMaster.domain.member.dto.MemberSearchResponse;
import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<MemberSearchResponse> searchMembers(MemberSearchCondition condition, long offset, int limit) {

		List<Long> ids = findIds(condition, offset, limit);

		if (ids.isEmpty()) {
			return Collections.emptyList();
		}

		return jpaQueryFactory
			.select(Projections.constructor(MemberSearchResponse.class,
				member.id,
				member.email,
				member.nickname,
				member.role,
				member.provider,
				member.createdAt
			))
			.from(member)
			.where(member.id.in(ids))
			.orderBy(member.id.desc())
			.fetch();
	}

	public Long countMembers(MemberSearchCondition condition, Long countLimit) {

		List<Long> ids = jpaQueryFactory
			.select(member.id)
			.from(member)
			.where(
				emailContains(condition.email()),
				nicknameContains(condition.nickname()),
				roleEq(condition.role()),
				providerEq(condition.provider()),
				member.isDeleted.isFalse()
			)
			.limit(countLimit)
			.fetch();

		return (long)ids.size();
	}

	private List<Long> findIds(MemberSearchCondition condition, long offset, int limit) {
		return jpaQueryFactory
			.select(member.id)
			.from(member)
			.where(
				emailContains(condition.email()),
				nicknameContains(condition.nickname()),
				roleEq(condition.role()),
				providerEq(condition.provider()),
				member.isDeleted.isFalse()
			)
			.orderBy(member.id.desc())
			.offset(offset)
			.limit(limit)
			.fetch();
	}

	private BooleanExpression emailContains(String email) {
		return email != null && !email.isBlank() ? member.email.containsIgnoreCase(email) : null;
	}

	private BooleanExpression nicknameContains(String nickname) {
		return nickname != null && !nickname.isBlank() ? member.nickname.containsIgnoreCase(nickname) : null;
	}

	private BooleanExpression roleEq(MemberRole role) {
		return role != null ? member.role.eq(role) : null;
	}

	private BooleanExpression providerEq(OAuth2Provider provider) {
		return provider != null ? member.provider.eq(provider) : null;
	}
}
