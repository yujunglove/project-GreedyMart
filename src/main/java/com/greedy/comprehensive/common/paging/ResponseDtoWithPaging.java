package com.greedy.comprehensive.common.paging;

import org.springframework.data.domain.Page;

import com.greedy.comprehensive.product.dto.ProductDto;

import lombok.Data;

@Data
public class ResponseDtoWithPaging {

    private Object data;
    private PagingButtonInfo pageInfo;


}
