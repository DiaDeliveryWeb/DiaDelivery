package com.dia.delivery.product.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductRequestDto {
    private String imageUrl;
    private String productName;
    private int price;
    private String description;

    private List<ProductRequestDto> ProductRequestDtoList= new ArrayList<>();


}
