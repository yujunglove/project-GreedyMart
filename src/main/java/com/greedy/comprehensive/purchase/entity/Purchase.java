package com.greedy.comprehensive.purchase.entity;

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
@Table(name="TBL_ORDER")
@SequenceGenerator(name="ORDER_SEQ_GENERATOR", sequenceName="SEQ_ORDER_CODE", initialValue=1, allocationSize=1)
@DynamicInsert
public class Purchase {
	
	@Id
	@Column(name="ORDER_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_SEQ_GENERATOR")
	private Long orderCode;
	
	@ManyToOne
	@JoinColumn(name="PRODUCT_CODE")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="ORDER_MEMBER")
	private Member orderer;
	
	@Column(name="ORDER_PHONE")
	private String orderPhone;
	
	@Column(name="ORDER_EMAIL")
	private String orderEmail;
	
	@Column(name="ORDER_RECEIVER")
	private String orderReceiver;
	
	@Column(name="ORDER_ADDRESS")
	private String orderAddress;
	
	@Column(name="ORDER_AMOUNT")
	private String orderAmount;
	
	@Column(name="ORDER_DATE")
	private String orderDate;
	
}
