package com.greedy.comprehensive.product.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProductDto {
	
	private Long productCode;
	private String productName;
	private String productPrice;
	private String productDescription;
	private String productOrderable;
	private CategoryDto category;
	
	/* DB 컬럼으로 존재하지는 않지만(entity의 필드로 선언하지 않는다) 
	 * 클라이언트에서 넘겨주는 상품 이미지 파일을 저장할 수 있는 필드 선언 */
	@JsonIgnore
	private MultipartFile productImage;
	
	private String productImgUrl;
	private Long productStock;

}
