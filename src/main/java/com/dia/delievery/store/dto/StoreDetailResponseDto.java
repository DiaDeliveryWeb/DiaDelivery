package com.dia.delievery.store.dto;

import com.dia.delievery.product.dto.ProductRequestDto;
import com.dia.delievery.product.dto.ProductResponseDto;
import com.dia.delievery.review.dto.ReviewsResponeDto;
import com.dia.delievery.review.entity.Reviews;
import com.dia.delievery.store.entity.Stores;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailResponseDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
    private List<ProductResponseDto> productResponseDtos;
    private List<ReviewsResponeDto> reviewsResponeDtos;

    public StoreDetailResponseDto(Stores stores) {
        this.name = stores.getName();
        this.introduction = stores.getIntroduction();
        this.imageUrl = stores.getImageUrl();
        this.category = stores.getCategory();
        this.productResponseDtos = stores.getProductsList().stream().map(ProductResponseDto::new).toList();
        this.reviewsResponeDtos = stores.getReviewsList().stream().map(ReviewsResponeDto::new).toList();
    }
}
