package com.dia.delivery.review.dto;

import com.dia.delivery.review.entity.Reviews;
import lombok.Getter;

@Getter
public class ReviewsResponeDto {
    private String content;
    private double rate;
    private String imageUrl;

    public ReviewsResponeDto(Reviews reviews){
        this.content = reviews.getContent();
        this.rate = reviews.getRate();
        this.imageUrl = reviews.getImageUrl();
    }
}
