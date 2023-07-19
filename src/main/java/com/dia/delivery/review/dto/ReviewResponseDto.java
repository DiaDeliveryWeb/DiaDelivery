package com.dia.delivery.review.dto;

import com.dia.delivery.review.entity.Reviews;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private String content;
    private double rate;
    private String imageUrl;

    public ReviewResponseDto(Reviews reviews){
        this.content = reviews.getContent();
        this.rate = reviews.getRate();
        this.imageUrl = reviews.getImageUrl();
    }
}