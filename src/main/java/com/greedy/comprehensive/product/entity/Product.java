package com.greedy.comprehensive.product.entity;

import lombok.Getter;

import javax.persistence.*;

//기본 생성자는 생성이 되니까 getter만 추가 한다.
@Getter
@Entity
@Table(name = "TBL_PRODUCT")
@SequenceGenerator(name = "PRODUCT_SEQ_GENERATOR",
        sequenceName = "SEQ_PRODUCT_CODE",
        initialValue = 1, allocationSize = 1)
public class Product {

//    PRODUCT_CODE	NUMBER
//    PRODUCT_NAME	VARCHAR2(100 BYTE)
//    PRODUCT_PRICE	VARCHAR2(100 BYTE)
//    PRODUCT_DESCRIPTION	VARCHAR2(1000 BYTE)
//    PRODUCT_ORDERABLE	VARCHAR2(5 BYTE)
//    CATEGORY_CODE	NUMBER
//    PRODUCT_IMAGE_URL	VARCHAR2(100 BYTE)
//    PRODUCT_STOCK	NUMBER

    @Id
    @Column(name = "PRODUCT_CODE")
    //SequenceGenerator를 사용하겠다.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ_GENERATOR")
    private Long productCode;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_PRICE")
    private String productPrice;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @Column(name = "PRODUCT_ORDERABLE")
    private String productOrderable;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_CODE")
    private Category category;

    @Column(name = "PRODUCT_IMAGE_URL")
    private String productImgUrl;

    @Column(name = "PRODUCT_STOCK")
    private Long productStock;



}