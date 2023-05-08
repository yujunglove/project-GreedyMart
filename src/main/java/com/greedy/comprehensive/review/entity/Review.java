package com.greedy.comprehensive.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.product.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter	
@Setter
@Entity
@Table(name = "TBL_REVIEW")
@SequenceGenerator(name = "REVIEW_SEQ_GENERATOR", sequenceName = "SEQ_REVIEW_CODE", initialValue = 1, allocationSize = 1)
@DynamicInsert
public class Review {
	
	@Id
	@Column(name = "REVIEW_CODE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
    private Long reviewCode;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_CODE")
    private Product product;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_CODE")
    private Member reviewer; 
	
	@Column(name = "REVIEW_TITLE")
    private String reviewTitle;
	
	@Column(name = "REVIEW_CONTENT")
    private String reviewContent;
	
	@Column(name = "REVIEW_CREATE_DATE")
    private String reviewCreateDate;


}
