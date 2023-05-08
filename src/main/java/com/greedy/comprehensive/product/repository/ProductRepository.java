package com.greedy.comprehensive.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.comprehensive.product.entity.Category;
import com.greedy.comprehensive.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	/* 1. 상품 목록 조회 - 페이징, 주문 불가 상품 제외(고객) */
	/* 연관 관계가 지연 로딩으로 되어 있을 경우 엔티티를 하나 조회하고 다시 다른 엔티티에 대해서 여러번 조회를 별도로 하게 되는 
	 * N + 1 문제가 발생하게 된다. (성능 이슈) fetch 조인을 사용하게 되면 한 번에 조인해서 결과를 가져오게 된다.
	 * @EntityGraph는 Data JPA에서 fetch 조인은 어노테이션으로 사용할 수 있도록 만들어준 기능이다.
	 * */
	@EntityGraph(attributePaths= {"category"})
	Page<Product> findByProductOrderable(Pageable pageable, String productOrderable);
	
	/* 2. 상품 목록 조회 - 페이징, 주문 불가 상품 포함(관리자) */
	/* JpaRepository에 이미 정의 되어 있는 findAll(Pageable pageable) 메소드 사용 가능하므로 별도 정의 필요 없음 */
	@EntityGraph(attributePaths= {"category"})
	Page<Product> findAll(Pageable pageable);
	
	/* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
	Page<Product> findByCategoryAndProductOrderable(Pageable pageable, Category category, String productOrderable);
	
	/* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
	@EntityGraph(attributePaths= {"category"})
	Page<Product> findByProductNameContainsAndProductOrderable(Pageable pageable, String productName, String productOrderable);
	
	/* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) 
	 * 쿼리 메소드로 구현 가능 findByProductCodeAndProductOrderable(Long productCode, String productOrderable) 
	 * JPQL을 사용해서 구현해보기 */
	@Query("SELECT p " +
		   "  FROM Product p " +
		   //"  JOIN fetch p.category " +
		   " WHERE p.productCode = :productCode " +
		   "   AND p.productOrderable = 'Y'")
	Optional<Product> findByProductCode(@Param("productCode") Long productCode);
	
	/* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) => findById 메소드 사용 */
	
	/* 7. 상품 등록(관리자) => save 메소드가 이미 정의 되어 있으므로 별도 정의 필요 없음 */
	
	/* 8. 상품 수정(관리자) => findById 메소드로 조회 후 필드 값 수정하면 변화를 감지하여 update 구문이 생성 되므로 별도의 정의 필요 없음 */
}










