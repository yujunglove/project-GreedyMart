package com.greedy.comprehensive.purchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.comprehensive.common.ResponseDto;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.purchase.dto.PurchaseDto;
import com.greedy.comprehensive.purchase.service.PurchaseService;

@RestController
@RequestMapping("/api/v1")
public class PurchaseController {

	private final PurchaseService purchaseService;
	
	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	
	/* 1. 주문 등록 */
	@PostMapping("/purchase")
	public ResponseEntity<ResponseDto> insertPurchase(@RequestBody PurchaseDto purchaseDto, 
			@AuthenticationPrincipal MemberDto member){
		
		purchaseDto.setOrderer(member);
		purchaseService.insertPurchase(purchaseDto);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "주문 완료"));
	}
	
	/* 2. 회원의 주문 목록 조회 */
	@GetMapping("/purchase")
	public ResponseEntity<ResponseDto> getPurchaseList(@AuthenticationPrincipal MemberDto member) {
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "주문 리스트 조회 완료", purchaseService.selectPurchaseList(member.getMemberCode())));
	}
	
	
	
	
	
	
	
}

















