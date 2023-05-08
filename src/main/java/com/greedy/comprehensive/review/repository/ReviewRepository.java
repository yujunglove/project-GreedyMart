package com.greedy.comprehensive.review.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.comprehensive.product.entity.Product;
import com.greedy.comprehensive.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	/* Pageable 객체를 전달하여 페이징 처리를 할 경우 jpql의 fetch 구문을 사용하는 것이 불가능하다. 
	 * 따라서 쿼리 메소드를 작성하여 @EntityGraph 를 사용한다. */
	@EntityGraph(attributePaths= {"product", "product.category", "reviewer"})
	Page<Review> findByProduct(Pageable pageable, Product product);
	
	/* 서버 실행 시 오류 발생 */
//	@Query("SELECT r FROM Review r JOIN fetch r.product JOIN fetch r.reviewer WHERE r.product.productCode = :productCode")
//	Page<Review> findByProduct(Pageable pageable, @Param("productCode") Long productCode);
	
	@Query("SELECT r FROM Review r "
		   + "JOIN fetch r.product JOIN fetch r.reviewer "
		  + "WHERE r.product.productCode = :productCode AND r.reviewer.memberCode = :memberCode")
	Optional<Review> findByProductAndReviewer(@Param("productCode") Long productCode, @Param("memberCode") Long memberCode);
	
}











