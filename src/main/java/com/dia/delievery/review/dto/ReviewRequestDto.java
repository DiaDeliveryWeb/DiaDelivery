package com.dia.delievery.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private String content;
    private double rate;
    private String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
