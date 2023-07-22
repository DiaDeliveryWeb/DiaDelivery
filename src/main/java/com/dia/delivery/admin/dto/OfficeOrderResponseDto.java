package com.dia.delivery.admin.dto;

import com.dia.delivery.order.OrderStatus;
import lombok.Getter;

@Getter
public class OfficeOrderResponseDto {
    private String orderNum;
    public OrderStatus orderStatus;

    public OfficeOrderResponseDto(String orderNum, OrderStatus orderStatus) {
        this.orderNum = orderNum;
        this.orderStatus = orderStatus;
    }
}
