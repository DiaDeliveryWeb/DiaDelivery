package com.dia.delivery.product.dto;

import com.dia.delivery.product.entity.Products;

public class ProductResponseDto {
    private String storeName;
    private String imageUrl;
    private String productName;
    private int price;
    private String description;

    public ProductResponseDto(Products products) {
        this.storeName = products.getStores().getName();
        this.imageUrl = products.getImageUrl();
        this.productName = products.getProductName();
        this.price = products.getPrice();
        this.description = products.getDescription();
    }

}