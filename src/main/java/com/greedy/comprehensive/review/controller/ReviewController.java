package com.greedy.comprehensive.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.comprehensive.common.ResponseDto;
import com.greedy.comprehensive.common.paging.Pagenation;
import com.greedy.comprehensive.common.paging.PagingButtonInfo;
import com.greedy.comprehensive.common.paging.ResponseDtoWithPaging;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.review.dto.ReviewDto;
import com.greedy.comprehensive.review.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

/* Entity, Dto, Repository, Service, Controller 작성 후 POSTMAN API 호출 테스트 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	/* 1. 상품별 리뷰 목록 조회 (페이징) */
	@GetMapping("/reviews/product/{productCode}")
	public ResponseEntity<ResponseDto> selectReviewListWithPaging(
			@PathVariable Long productCode, 
			@RequestParam(name="page", defaultValue="1") int page) {
		
		log.info("[ReviewController] selectReviewListWithPaging Start ===================================");
        log.info("[ReviewController] selectReviewListWithPaging : " + page);
        log.info("[ReviewController] productCode : " + productCode);
        
        Page <ReviewDto> reviewDtoList = reviewService.selectReviewListWithPaging(page, productCode);
        
        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(reviewDtoList);
        
        log.info("[ReviewController] reviewDtoList : " + reviewDtoList.getContent());
        log.info("[ReviewController] pageInfo : " + pageInfo);
	
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(reviewDtoList.getContent());	
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
	
	/* 2. 리뷰 코드로 리뷰 상세 조회 */
	@GetMapping("/reviews/{reviewCode}")
	public ResponseEntity<ResponseDto> selectReviewDetail(@PathVariable Long reviewCode) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", reviewService.selectReviewDetail(reviewCode)));
	}
	
	/* 3. 리뷰 작성 */
	@PostMapping("/reviews")
	public ResponseEntity<ResponseDto> insertReview(@RequestBody ReviewDto reviewDto, 
			@AuthenticationPrincipal MemberDto member) {
	
		reviewDto.setReviewer(member);
		reviewService.insertReview(reviewDto);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "리뷰 입력 성공"));	
	}
	
	/* 4. 해당 상품 리뷰 작성 여부 확인 */
	@GetMapping("/reviews/member/product/{productCode}")
	public ResponseEntity<ResponseDto> selectMemberReview(@PathVariable Long productCode
			, @AuthenticationPrincipal MemberDto member) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "리뷰 조회 성공", reviewService.selectMemberReview(productCode, member.getMemberCode())));
	}
	
	
	
	
	
	
	
	
	
	
}
