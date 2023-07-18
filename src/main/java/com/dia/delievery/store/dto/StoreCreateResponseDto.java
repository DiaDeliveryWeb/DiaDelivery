package com.dia.delievery.store.dto;

import com.dia.delievery.product.dto.ProductResponseDto;
import com.dia.delievery.store.entity.Stores;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreCreateResponseDto { // 가게 등록 ResponseDto
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
    private List<ProductResponseDto> productList;

    public StoreCreateResponseDto(Stores store) {
        this.name = store.getName();
        this.introduction = store.getIntroduction();
        this.imageUrl = store.getImageUrl();
        this.category = store.getCategory();
        this.productList = store.getProductsList().stream().map(ProductResponseDto::new).toList();
    }
}
