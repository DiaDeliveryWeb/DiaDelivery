package com.dia.delivery.order.dto;

import com.dia.delivery.order.OrderStatus;
import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.product.entity.Products;
import com.dia.delivery.review.dto.ReviewResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private String storeName;
    private List<ProductResponseDto> productResponseDtos;
    private ReviewResponseDto review;
    private String username;
    private OrderStatus orderStatus;
    private String orderNum;

    public OrderResponseDto(String storeName, List<Products> products, String username, OrderStatus orderStatus) {
        this.storeName = storeName;
        this.username = username;
        this.orderStatus = orderStatus;
        this.productResponseDtos = products.stream().map(ProductResponseDto::new).toList();
    }

    public OrderResponseDto(Orders orders){
        this.orderId = orders.getId();
        this.storeName = orders.getProductOrdersList().get(0).getProducts().getStores().getName();
        this.username = orders.getUser().getUsername();
        this.orderStatus = orders.getOrderStatus();
        this.orderNum = orders.getOrderNum();
        this.review = new ReviewResponseDto(orders.getReviews());
        this.productResponseDtos = orders.getProductOrdersList().stream().map((productOrders) -> new ProductResponseDto(productOrders.getProducts())).toList();
    }
}