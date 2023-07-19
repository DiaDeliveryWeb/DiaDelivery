package com.dia.delivery.order.dto;

import com.dia.delivery.order.OrderStatus;
import com.dia.delivery.order.entity.Orders;
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

    public OrderResponseDto(Orders orders){
        this.storeName = orders.getProductOrdersList().get(0).getProducts().getStores().getName();
        this.username = orders.getUsers().getUsername();
        this.orderStatus = orders.getOrderStatus();
        this.productResponseDtos = orders.getProductOrdersList().stream().map((productOrders) -> new ProductResponseDto(productOrders.getProducts())).toList();
    }
}