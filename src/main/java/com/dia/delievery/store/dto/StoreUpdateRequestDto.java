package com.dia.delievery.store.dto;

import lombok.Getter;

@Getter
public class StoreUpdateRequestDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private String category;
}
