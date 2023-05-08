package com.greedy.comprehensive.review.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.comprehensive.product.entity.Product;
import com.greedy.comprehensive.product.repository.ProductRepository;
import com.greedy.comprehensive.review.dto.ReviewDto;
import com.greedy.comprehensive.review.entity.Review;
import com.greedy.comprehensive.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;
	
	public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, ModelMapper modelMapper) {
		this.reviewRepository = reviewRepository;
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 1. 상품별 리뷰 목록 조회 (페이징) */
	public Page<ReviewDto> selectReviewListWithPaging(int page, Long productCode) {
		 log.info("[ReviewService] getReviewList Start ==============================");
		 
		 Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("reviewCode").descending());
		 
		 Product foundProduct = productRepository.findByProductCode(productCode)
				 					.orElseThrow(() -> new RuntimeException("존재하는 상품이 아닙니다."));
		 
		 Page<ReviewDto> reviewDtoList = reviewRepository.findByProduct(pageable, foundProduct)
				 							.map(review -> modelMapper.map(review, ReviewDto.class));
		
		 log.info("[ReviewService] getReviewList End ==============================");
		 
		return reviewDtoList;
	}

	/* 2. 리뷰 코드로 리뷰 상세 조회 */
	public ReviewDto selectReviewDetail(Long reviewCode) {
		
		log.info("[ReviewService] selectReviewDetail Start ===================================");
		log.info("[ReviewService] reviewCode : {}", reviewCode);
		
		ReviewDto reviewDto = modelMapper.map(reviewRepository.findById(reviewCode)
				.orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다.")), ReviewDto.class);
		
		log.info("[ReviewService] selectReviewDetail End ==============================");

		return reviewDto;
	}
	
	/* 3. 리뷰 작성 */
	@Transactional
	public void insertReview(ReviewDto reviewDto) {
		
		log.info("[ReviewService] insertReview Start ===================================");
		log.info("[ReviewService] reviewDto : {}", reviewDto);
		
		reviewRepository.save(modelMapper.map(reviewDto, Review.class));
	
		log.info("[ReviewService] insertReview End ==============================");
	}

	/* 4. 해당 상품 리뷰 작성 여부 확인 */
	public ReviewDto selectMemberReview(Long productCode, Long memberCode) {
		
		Review review = reviewRepository.findByProductAndReviewer(productCode, memberCode).orElse(new Review());
		
		return modelMapper.map(review, ReviewDto.class);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
