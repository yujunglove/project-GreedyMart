package com.greedy.comprehensive.product.service;

import com.greedy.comprehensive.product.dto.ProductDto;
import com.greedy.comprehensive.product.entity.Category;
import com.greedy.comprehensive.product.entity.Product;
import com.greedy.comprehensive.product.repository.CategoryRepository;
import com.greedy.comprehensive.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
public class ProductService {

    //의존성 주입
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // 1. 상품 목록 조회 - 페이징, 주문 불가 상품 제외(고객)
    public Page<ProductDto> selectProductList(int page) {

       // log.info("[ProductService] selectProductList Start ================");

        //총 10개씩만 보고싶다. 프로덕트 기준으로 역순으로 정렬한 상태를 원한다.
        Pageable pageable = PageRequest.of(page -1, 10, Sort.by("productCode").descending());

        Page<Product> productList = productRepository.findByProductOrderable(pageable,"Y");
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));


		/* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
//		productDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + product.getProductImgUrl()));
		

       // log.info("[ProductService] productDtoList.getContent() : {}",productDtoList.getContent());
        //log.info("[ProductService] selectProductList end ================");
        return productDtoList;
    }
    /* 2. 상품 목록 조회 - 페이징, 주문 불가 상품 포함(관리자) */
    public Page<ProductDto> selectProductListForAdmin(int page) {

        log.info("[ProductService] selectProductListForAdmin start ============================== ");

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());

        Page<Product> productList = productRepository.findAll(pageable);
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));

        /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
        productDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + product.getProductImgUrl()));

        log.info("[ProductService] productDtoList.getContent() : {}", productDtoList.getContent());

        log.info("[ProductService] selectProductListForAdmin end ============================== ");

        return productDtoList;
    }

    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    public Page<ProductDto> selectProductListByCategory(int page, Long categoryCode) {

        log.info("[ProductService] selectProductListByCategory start ============================== ");

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());

        /* 전달할 카테고리 엔티티를 먼저 조회한다. */
        Category findCategory = categoryRepository.findById(categoryCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. categoryCode = "+ categoryCode));

        Page<Product> productList = productRepository.findByCategoryAndProductOrderable(pageable, findCategory, "Y");
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));

        /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
        productDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + product.getProductImgUrl()));

        log.info("[ProductService] productDtoList.getContent() : {}", productDtoList.getContent());

        log.info("[ProductService] selectProductListByCategory end ============================== ");

        return productDtoList;
    }


    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
    public Page<ProductDto> selectProductListByProductName(int page, String productName) {

        log.info("[ProductService] selectProductListByProductName start ============================== ");

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());

        Page<Product> productList = productRepository.findByProductNameContainsAndProductOrderable(pageable, productName, "Y");
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));

        /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
        productDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + product.getProductImgUrl()));

        log.info("[ProductService] productDtoList.getContent() : {}", productDtoList.getContent());

        log.info("[ProductService] selectProductListByProductName end ============================== ");

        return productDtoList;
    }





}
