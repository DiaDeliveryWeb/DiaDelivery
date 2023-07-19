package com.dia.delivery.product.dto;

import com.dia.delivery.product.entity.Products;

public class ProductResponseDto {
    public String storeName;
    public String imageUrl;
    public String productName;
    public int price;
    public String description;

    public ProductResponseDto(Products products) {
        this.storeName = products.getStores().getName();
        this.imageUrl = products.getImageUrl();
        this.productName = products.getProductName();
        this.price = products.getPrice();
        this.description = products.getDescription();
    }

}
