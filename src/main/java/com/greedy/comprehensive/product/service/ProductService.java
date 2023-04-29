package com.greedy.comprehensive.product.service;

import com.greedy.comprehensive.product.dto.ProductDto;
import com.greedy.comprehensive.product.entity.Category;
import com.greedy.comprehensive.product.entity.Product;
import com.greedy.comprehensive.product.repository.CategoryRepository;
import com.greedy.comprehensive.product.repository.ProductRepository;
import com.greedy.comprehensive.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductService {

    //의존성 주입
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Value("${image.image-url}")
    private String IMAGE_URL;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // 1. 상품 목록 조회 - 페이징, 주문 불가 상품 제외(고객)
    public Page<ProductDto> selectProductList(int page) {

        // log.info("[ProductService] selectProductList Start ================");

        //총 10개씩만 보고싶다. 프로덕트 기준으로 역순으로 정렬한 상태를 원한다.
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("productCode").descending());

        Page<Product> productList = productRepository.findByProductOrderable(pageable, "Y");
        Page<ProductDto> productDtoList = productList.map(product -> modelMapper.map(product, ProductDto.class));


        /* 클라이언트 측에서 서버에 저장 된 이미지 요청 시 필요한 주소로 가공 */
		productDtoList.forEach(product -> product.setProductImgUrl(IMAGE_URL + product.getProductImgUrl()));


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
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. categoryCode = " + categoryCode));

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

    //5. 상품 상세 조회 - productCode로 상품1개조회, 주문 불가 상품제외(고객)
    public ProductDto selectProduct(Long productCode) {
//        log.info("[ProductService] selectProductListByProductName start ============================== ");
//        @Query("SELECT p " +
//                "  FROM Product p " +
//                " WHERE p.productCode = :productCode " +
//                "   AND p.productOrderable = 'Y'")
//        Optional<Product> findByProductCode(@Param("productCode") Long productCode);
        //위를 참고로 해보자.

        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 상품이 없습니다. productCode=" + productCode));

        //가공 하기
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setProductImgUrl(IMAGE_URL + productDto.getProductImgUrl());

        log.info("[ProductService] productDto : {} ", productDto);
        log.info("[ProductService] selectProductListByProductName end ============================== ");
        return productDto;
    }

    //6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) -> findById 메소드 사용
    public ProductDto selectProductForAdmin(Long productCode) {
//        log.info("[ProductService] selectProductListByProductName start ============================== ");
//        @Query("SELECT p " +
//                "  FROM Product p " +
//                " WHERE p.productCode = :productCode " +
//                "   AND p.productOrderable = 'Y'")
//        Optional<Product> findByProductCode(@Param("productCode") Long productCode);
        //위를 참고로 해보자.

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 상품이 없습니다. productCode=" + productCode));

        //가공 하기
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setProductImgUrl(IMAGE_URL + productDto.getProductImgUrl());

        log.info("[ProductService] productDto : {} ", productDto);
        log.info("[ProductService] selectProductListByProductName end ============================== ");
        return productDto;
    }

    //7.상품 등록

    public void insertProduct(ProductDto productDto) {
        //알맞게 클라이언트로부터 값을 잘 가져왔는지? 알아보기

        //영문자랑 숫자랑 -가 섞여서 나오는데 만약 -이면 ""빈 문자로 바꿔준다 라고 치환하는 코드
        String imageName = UUID.randomUUID().toString().replace("-", "");

        try {
            String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productDto.getProductImage());
            productDto.setProductImgUrl(replaceFileName);

            //새로운 값을 저장하려고 할 때 사용하는 save . 영속성 객체로 변환하기
            productRepository.save(modelMapper.map(productDto, Product.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    /* 8. 상품 수정 */
    @Transactional
    public void updateProduct(ProductDto productDto) {
        log.info("[ProductService] insertProduct start ============================== ");
        log.info("[ProductService] productDto : {}", productDto);

        Product originProduct = productRepository.findById(productDto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 코드의 상품이 없습니다. productCode=" + productDto.getProductCode()));

        try {
            /* 이미지를 변경하는 경우 */
            if(productDto.getProductImage() != null) {

                /* 새로 입력 된 이미지 저장 */
                String imageName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productDto.getProductImage());

                /* 기존에 저장 된 이미지 삭제 */
                FileUploadUtils.deleteFile(IMAGE_DIR, originProduct.getProductImgUrl());

                /* DB에 저장 될 imageUrl 값을 수정 */
                originProduct.setProductImgUrl(replaceFileName);
            }

            /* 이미지를 변경하지 않는 경우에는 별도의 처리가 필요 없음 */

            /* 조회했던 기존 엔티티의 내용을 수정 -> 별도의 수정 메소드를 정의해서 사용하면 다른 방식의 수정을 막을 수 있다. */
            originProduct.update(
                    productDto.getProductName(),
                    productDto.getProductPrice(),
                    productDto.getProductDescription(),
                    productDto.getProductOrderable(),
                    modelMapper.map(productDto.getCategory(), Category.class),
                    productDto.getProductStock()
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("[ProductService] insertProduct end ============================== ");
    }


}
