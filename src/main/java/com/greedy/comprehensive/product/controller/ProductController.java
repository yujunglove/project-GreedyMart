package com.greedy.comprehensive.product.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.comprehensive.common.ResponseDto;
import com.greedy.comprehensive.common.paging.Pagenation;
import com.greedy.comprehensive.common.paging.PagingButtonInfo;
import com.greedy.comprehensive.common.paging.ResponseDtoWithPaging;
import com.greedy.comprehensive.product.dto.ProductDto;
import com.greedy.comprehensive.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProductController {

	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	/* 1. 상품 목록 조회 - 페이징, 주문 불가 상품 제외(고객) */
	@GetMapping("/products")
	public ResponseEntity<ResponseDto> selectProductList(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[ProductController] : selectProductList start ==================================== ");
		log.info("[ProductController] : page : {}", page);
		
		Page<ProductDto> productDtoList = productService.selectProductList(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);
		
		log.info("[ProductController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(productDtoList.getContent());
		
		log.info("[ProductController] : selectProductList end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}
	
	/* 2. 상품 목록 조회 - 페이징, 주문 불가 상품 포함(관리자) */
	@GetMapping("/products-management")
	public ResponseEntity<ResponseDto> selectProductListForAdmin(@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[ProductController] : selectProductListForAdmin start ==================================== ");
		log.info("[ProductController] : page : {}", page);
		
		Page<ProductDto> productDtoList = productService.selectProductListForAdmin(page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);
		
		log.info("[ProductController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(productDtoList.getContent());
		
		log.info("[ProductController] : selectProductListForAdmin end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
		
	}
	
	/* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
	@GetMapping("/products/categories/{categoryCode}")
	public ResponseEntity<ResponseDto> selectProductListByCategory(
			@RequestParam(name="page", defaultValue="1") int page, @PathVariable Long categoryCode){
		
		log.info("[ProductController] : selectProductListByCategory start ==================================== ");
		log.info("[ProductController] : page : {}", page);
		
		Page<ProductDto> productDtoList = productService.selectProductListByCategory(page, categoryCode);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);
		
		log.info("[ProductController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(productDtoList.getContent());
		
		log.info("[ProductController] : selectProductListByCategory end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	
	/* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) 
	 * productName 값은 parameter로 전달 받도록 하여 URL 설정 */
	@GetMapping("/products/search")
	public ResponseEntity<ResponseDto> selectProductListByProductName(
			@RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="search") String productName) {
		
		log.info("[ProductController] : selectProductListByProductName start ==================================== ");
		log.info("[ProductController] : page : {}", page);
		log.info("[ProductController] : productName : {}", productName);
		
		Page<ProductDto> productDtoList = productService.selectProductListByProductName(page, productName);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);
		
		log.info("[ProductController] : pageInfo : {}", pageInfo);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(productDtoList.getContent());
		
		log.info("[ProductController] : selectProductListByProductName end ==================================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
	@GetMapping("products/{productCode}")
	public ResponseEntity<ResponseDto> selectProductDetail(@PathVariable Long productCode) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 성공", productService.selectProduct(productCode)));
	}
	
	/* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) => findById 메소드 사용 */
	@GetMapping("products-management/{productCode}")
	public ResponseEntity<ResponseDto> selectProductDetailForAdmin(@PathVariable Long productCode) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 성공", productService.selectProductForAdmin(productCode)));
	}
	
	/* 7. 상품 등록 */
	@PostMapping("/products")
	public ResponseEntity<ResponseDto> insertProduct(@ModelAttribute ProductDto productDto) {
	
		productService.insertProduct(productDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "상품 등록 성공"));
	}
	
	/* 8. 상품 수정 */
	@PutMapping("/products")
	public ResponseEntity<ResponseDto> updateProduct(@ModelAttribute ProductDto productDto) {
		
		productService.updateProduct(productDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "상품 수정 성공"));
		
	}
	
}
