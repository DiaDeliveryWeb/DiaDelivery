package com.dia.delivery.common.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private String msg;
    private int statusCode;

    public ApiResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
