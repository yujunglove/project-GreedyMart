package com.greedy.comprehensive.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.comprehensive.member.entity.Member;

public interface MemberRepository extends JpaRepository <Member, Long> {

	/* 이메일 중복 여부 확인 시 조회 메소드 */
	Member findByMemberEmail(String memberEmail);

	/* 아이디로 유저 조회 메소드 */
	Optional<Member> findByMemberId(String memberId);

}
