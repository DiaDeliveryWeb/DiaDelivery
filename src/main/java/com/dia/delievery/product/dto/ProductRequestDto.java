package com.dia.delievery.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductRequestDto {
    private String productName;
    private int price;
    private String description;
}
