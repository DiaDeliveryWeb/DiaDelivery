package com.dia.delivery.admin.dto;

import lombok.Getter;

@Getter
public class OfficeUserResponseDto {
    private String username;

    public OfficeUserResponseDto(String username) {
        this.username = username;
    }
}
