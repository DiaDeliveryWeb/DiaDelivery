package com.dia.delivery.store.dto;

import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.store.entity.Stores;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreOneResponseDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
    private List<Reviews> reviewsList;
    public StoreOneResponseDto(Stores store) {
        this.name = store.getName();
        this.introduction = store.getIntroduction();
        this.imageUrl = store.getImageUrl();
        this.category = store.getCategory();
        this.reviewsList = store.getReviewsList();
    }
}
