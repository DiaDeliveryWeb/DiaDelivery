package com.dia.delivery.store.dto;

import com.dia.delivery.product.dto.ProductRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class StoreRequestDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
    private List<ProductRequestDto> productList;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
