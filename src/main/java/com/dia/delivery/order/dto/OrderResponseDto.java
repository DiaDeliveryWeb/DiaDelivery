package com.dia.delivery.order.dto;

import com.dia.delivery.order.OrderStatus;
import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.product.entity.Products;
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
