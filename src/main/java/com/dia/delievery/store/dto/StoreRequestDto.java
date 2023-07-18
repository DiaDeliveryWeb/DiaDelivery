package com.dia.delievery.store.dto;

import com.dia.delievery.product.dto.ProductRequestDto;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreRequestDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
    private List<ProductRequestDto> productList;

}
