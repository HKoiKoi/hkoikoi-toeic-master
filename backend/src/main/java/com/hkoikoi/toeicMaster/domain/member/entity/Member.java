package com.hkoikoi.toeicMaster.domain.member.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.hkoikoi.toeicMaster.domain.member.enums.MemberRole;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@DynamicUpdate
@SQLRestriction("is_deleted = false")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	name = "member",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_member_nickname",
			columnNames = {"nickname"}
		),
		@UniqueConstraint(
			name = "uk_member_provider_provider_id",
			columnNames = {"provider", "provider_id"}
		)
	}
)
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE member_id = ?")
public class Member {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false)
	String email;

	@Column(nullable = false, length = 10)
	String nickname;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	OAuth2Provider provider;

	@Column(name = "provider_id", nullable = false)
	String providerId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	MemberRole role;

	@Column(name = "is_deleted", nullable = false)
	Boolean isDeleted;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	LocalDateTime updatedAt;

	public static Member create(String email, String nickname, OAuth2Provider provider, String providerId) {

		Member member = new Member();

		member.email = email;
		member.nickname = nickname;
		member.provider = provider;
		member.providerId = providerId;
		member.role = MemberRole.BASIC;
		member.isDeleted = Boolean.FALSE;

		return member;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateRole(MemberRole role) {
		this.role = role;
	}

	public void delete() {
		this.isDeleted = Boolean.TRUE;
	}
}
