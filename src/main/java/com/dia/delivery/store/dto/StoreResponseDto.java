package com.dia.delivery.store.dto;

import com.dia.delivery.store.entity.Stores;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
    public StoreResponseDto(Stores store) {
        this.name = store.getName();
        this.introduction = store.getIntroduction();
        this.imageUrl = store.getImageUrl();
        this.category = store.getCategory();
    }
}
