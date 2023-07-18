package com.dia.delievery.product.dto;

import com.dia.delievery.product.entity.Products;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private String productName;
    private int price;
    private String description;

    public ProductResponseDto(Products products){
        this.productName = products.getProductName();
        this.price = products.getPrice();
        this.description = products.getDescription();
    }
}
