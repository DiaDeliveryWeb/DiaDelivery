package com.dia.delivery.product.dto;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String imageUrl;
    private String productName;
    private int price;
    private String description;
}
