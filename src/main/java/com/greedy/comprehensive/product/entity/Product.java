package com.greedy.comprehensive.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="TBL_PRODUCT")
@SequenceGenerator(name="PRODUCT_SEQ_GENERATOR",
		sequenceName="SEQ_PRODUCT_CODE",
		initialValue=1, allocationSize=1)
public class Product {
	
	@Id
	@Column(name="PRODUCT_CODE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCT_SEQ_GENERATOR")
	private Long productCode;
	
	@Column(name="PRODUCT_NAME")
	private String productName;
	
	@Column(name="PRODUCT_PRICE")
	private String productPrice;
	
	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;
	
	@Column(name="PRODUCT_ORDERABLE")
	private String productOrderable;
	
	/* cascade = CascadeType.PERSIST : 영속성 전이 설정을 넣으면 Category에 새로운 값이 있을 경우 insert 될 수 있음 */
	@ManyToOne
	@JoinColumn(name = "CATEGORY_CODE")
	private Category category;
	
	@Column(name="PRODUCT_IMAGE_URL")
	private String productImgUrl;
	
	@Column(name="PRODUCT_STOCK")
	private Long productStock;
	
	/* Product entity 수정 용도의 메소드를 별도로 정의 */
	public void update(String productName, String productPrice, String productDescription, 
			String productOrderable, Category category, Long productStock) {
		
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
		this.productOrderable = productOrderable;
		this.category = category;
		this.productStock = productStock;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
