package com.greedy.comprehensive.purchase.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.purchase.entity.Purchase;

public interface PurchaseRepository extends JpaRepository <Purchase, Long> {
	
	/* 1. 주문 내역 저장 -> save 메소드 */
	
	/* 2. 마이페이지에서 결제 내역 조회 -> 페이징 없이 최신순 정렬만 */
	/* N+1 문제를 방지하기 위해서 fetch join(jpql), @EntityGraph(쿼리메소드) 사용 */
	@EntityGraph(attributePaths = {"product", "product.category"})
	List<Purchase> findByOrderer(Member member, Sort order);

}
