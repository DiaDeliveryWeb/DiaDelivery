package com.dia.delivery.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OfficeReviewResponseDto {
    private String author;
    private String orderNum;
    private String content;
    private double rate;

}
