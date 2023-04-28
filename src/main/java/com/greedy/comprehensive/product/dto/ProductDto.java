package com.greedy.comprehensive.product.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long productCode;

    private String productName;

    private String productPrice;

    private String productDescription;

    private String productOrderable;

    private CategoryDto category;

    private String productImgUrl;

    private Long productStock;






}
