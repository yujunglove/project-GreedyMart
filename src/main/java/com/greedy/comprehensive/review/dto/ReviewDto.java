package com.greedy.comprehensive.review.dto;

import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.product.dto.ProductDto;

import lombok.Data;

@Data
public class ReviewDto {

    private Long reviewCode;
    private ProductDto product;
    private MemberDto reviewer;
    private String reviewTitle;
    private String reviewContent;
    private String reviewCreateDate;

}
