package com.greedy.comprehensive.purchase.dto;

import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.product.dto.ProductDto;

import lombok.Data;

@Data
public class PurchaseDto {
	
	private Long orderCode;
	private ProductDto product;
	private MemberDto orderer;
	private String orderPhone;
	private String orderEmail;
	private String orderReceiver;
	private String orderAddress;
	private Long orderAmount;
	private String orderDate;

}
