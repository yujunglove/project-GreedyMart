package com.greedy.comprehensive.controller;

import com.greedy.comprehensive.common.ResponseDto;
import com.greedy.comprehensive.common.paging.Pagenation;
import com.greedy.comprehensive.common.paging.PagingButtonInfo;
import com.greedy.comprehensive.common.paging.ResponseDtoWithPaging;
import com.greedy.comprehensive.product.dto.CategoryDto;
import com.greedy.comprehensive.product.dto.ProductDto;
import com.greedy.comprehensive.product.entity.Product;
import com.greedy.comprehensive.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //1. 상품 목록 조회 - 페이징, 주문 불가 상품 제외(고객)
    @GetMapping("/products")
    public ResponseEntity<ResponseDto> selectProductList(@RequestParam(name = "page", defaultValue = "1") int page) {

        //log.info("[ProductController] : selectProductList start =====================");
        //log.info("[ProductController] :page : {} ", page);

        Page<ProductDto> productDtoList = productService.selectProductList(page);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(productDtoList);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회성공",responseDtoWithPaging));
    }

    //2. 상품 목록 조회 - 페이징, 주문 불가 상품 포함
    @GetMapping("/products-management")
    public ResponseEntity<ResponseDto> selectProductListForAdmin(@RequestParam(name = "page", defaultValue = "1") int page) {

        //log.info("[ProductController] : selectProductList start =====================");
        //log.info("[ProductController] :page : {} ", page);

        Page<ProductDto> productDtoList = productService.selectProductListForAdmin(page);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(productDtoList);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회성공",responseDtoWithPaging));
    }


    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    @GetMapping("/products/categories/{categoryCode}")
    public ResponseEntity<ResponseDto> selectProductListByCategory(
            @RequestParam(name="page", defaultValue="1") int page, @PathVariable Long categoryCode){

        log.info("[ProductController] : selectProductListByCategory start ==================================== ");
        log.info("[ProductController] : page : {}", page);

        Page<ProductDto> productDtoList = productService.selectProductListByCategory(page, categoryCode);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);

        log.info("[ProductController] : pageInfo : {}", pageInfo);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(productDtoList.getContent());

        log.info("[ProductController] : selectProductListByCategory end ==================================== ");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객)
     * productName 값은 parameter로 전달 받도록 하여 URL 설정 */

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객)
     * productName 값은 parameter로 전달 받도록 하여 URL 설정 */
    @GetMapping("/products/search")
    public ResponseEntity<ResponseDto> selectProductListByProductName(
            @RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="search") String productName) {

        log.info("[ProductController] : selectProductListByProductName start ==================================== ");
        log.info("[ProductController] : page : {}", page);
        log.info("[ProductController] : productName : {}", productName);

        Page<ProductDto> productDtoList = productService.selectProductListByProductName(page, productName);

        PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(productDtoList);

        log.info("[ProductController] : pageInfo : {}", pageInfo);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(pageInfo);
        responseDtoWithPaging.setData(productDtoList.getContent());

        log.info("[ProductController] : selectProductListByProductName end ==================================== ");

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }



}


