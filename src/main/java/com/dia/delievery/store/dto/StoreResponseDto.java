package com.dia.delievery.store.dto;

import com.dia.delievery.store.entity.Stores;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;

    public StoreResponseDto(Stores stores){
        this.name = stores.getName();
        this.introduction = stores.getIntroduction();
        this.imageUrl = stores.getImageUrl();
        this.category = stores.getCategory();
    }
}
