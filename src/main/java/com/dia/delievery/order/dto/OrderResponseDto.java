package com.dia.delievery.order.dto;

import com.dia.delievery.order.OrderStatus;
import com.dia.delievery.order.entity.Orders;
import com.dia.delievery.product.dto.ProductResponseDto;
import com.dia.delievery.product.entity.Products;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {
    private String storeName;
    private List<ProductResponseDto> productResponseDtos;
    private String username;
    private OrderStatus orderStatus;

    public OrderResponseDto(String storeName, List<Products> products, String username, OrderStatus orderStatus) {
        this.storeName = storeName;
        this.username = username;
        this.orderStatus = orderStatus;
        this.productResponseDtos = products.stream().map(ProductResponseDto::new).toList();
    }
}
