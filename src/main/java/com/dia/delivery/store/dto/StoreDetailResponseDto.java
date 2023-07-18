package com.dia.delivery.store.dto;

import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.review.dto.ReviewsResponeDto;
import com.dia.delivery.store.entity.Stores;
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
