package com.dia.delivery.order.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {
    List<Long> productList;
}
