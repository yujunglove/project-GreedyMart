package com.greedy.comprehensive.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="TBL_CATEGORY")
public class Category {
	
	@Id
	@Column(name="CATEGORY_CODE")
	private Long categoryCode;
	
	@Column(name="CATEGORY_NAME")
	private String categoryName;
	
}
