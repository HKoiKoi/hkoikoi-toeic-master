package com.hkoikoi.toeicMaster.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hkoikoi.toeicMaster.domain.member.entity.Member;
import com.hkoikoi.toeicMaster.domain.member.enums.OAuth2Provider;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByProviderAndProviderId(OAuth2Provider provider, String providerId);

	boolean existsByNickname(String nickname);
}
