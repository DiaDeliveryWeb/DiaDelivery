package com.dia.delivery.product.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProductRequestDto {
    private String imageUrl;
    private String productName;
    private int price;
    private String description;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
