package com.greedy.comprehensive.purchase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.comprehensive.exception.UserNotFoundException;
import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.member.repository.MemberRepository;
import com.greedy.comprehensive.product.entity.Product;
import com.greedy.comprehensive.product.repository.ProductRepository;
import com.greedy.comprehensive.purchase.dto.PurchaseDto;
import com.greedy.comprehensive.purchase.entity.Purchase;
import com.greedy.comprehensive.purchase.repository.PurchaseRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;
	
	public PurchaseService(PurchaseRepository purchaseRepository, ProductRepository productRepository, 
			MemberRepository memberRepository, ModelMapper modelMapper) {
		this.purchaseRepository = purchaseRepository;
		this.productRepository = productRepository;
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 1. 주문 결제 저장 */
	@Transactional
	public void insertPurchase(PurchaseDto purchaseDto) {
		
		log.info("[PurchaseService] insertPurchase start ============================== ");
		log.info("[PurchaseService] purchaseDto : {}", purchaseDto);
		
		// Order 테이블에 주문 정보 입력
		purchaseRepository.save(modelMapper.map(purchaseDto, Purchase.class));
		
		// Product 테이블의 Product 재고 업데이트 -> 재고 부족 시 실패
		Product foundProduct = productRepository.findByProductCode(purchaseDto.getProduct().getProductCode())
				.orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. productCode="+purchaseDto.getProduct().getProductCode()));
		
		foundProduct.setProductStock(foundProduct.getProductStock() - purchaseDto.getOrderAmount());
		
		if(foundProduct.getProductStock() < 0) throw new RuntimeException("주문할 수 있는 재고가 부족합니다.");
		
		log.info("[PurchaseService] insertPurchase end ============================== ");
	}
	
	/* 2. 회원의 주문 목록 조회 */
	public List<PurchaseDto> selectPurchaseList(Long memberCode) {
		log.info("[PurchaseService] selectPurchaseList start ============================== ");
		log.info("[PurchaseService] memberCode : {}", memberCode);
		
		Member member = memberRepository.findById(memberCode)
				.orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
		
		List<PurchaseDto> purchaseList = purchaseRepository.findByOrderer(member, Sort.by("orderCode").descending())
				.stream().map(purchase -> modelMapper.map(purchase, PurchaseDto.class)).collect(Collectors.toList());
		
		log.info("[PurchaseService] purchaseList : {}", purchaseList);
		log.info("[PurchaseService] selectPurchaseList end ============================== ");
		return purchaseList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
