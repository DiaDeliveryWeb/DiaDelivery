package com.dia.delievery.order.service;

import com.dia.delievery.order.OrderStatus;
import com.dia.delievery.order.dto.OrderRequestDto;
import com.dia.delievery.order.dto.OrderResponseDto;
import com.dia.delievery.order.entity.Orders;
import com.dia.delievery.order.repository.OrderRepository;
import com.dia.delievery.product.entity.Products;
import com.dia.delievery.product.repository.ProductRepository;
import com.dia.delievery.productorder.entity.ProductOrders;
import com.dia.delievery.productorder.repository.ProductOrderRepository;
import com.dia.delievery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    public OrderResponseDto save(Users user, OrderRequestDto requestDto) {
        List<Products> products = productRepository.findAllByIdIn(requestDto.getProductList());
        Orders orders = new Orders(user);
        for (Products product : products) {
            productOrderRepository.save(new ProductOrders(orders, product));
        }
        return new OrderResponseDto(products.get(0).getStores().getName(), products, user.getUsername(), orders.getOrderStatus());
    }

    public void accept(Users user, String orderNum) {
        Orders orders = orderRepository.findByOrderNum(orderNum).orElseThrow(()->new IllegalArgumentException("해당 주문내역이 없습니다"));
        orders.setOrderStatus(OrderStatus.주문완료);
    }

    public void cancel(Users user, String orderNum) {
        Orders orders = orderRepository.findByOrderNum(orderNum).orElseThrow(()->new IllegalArgumentException("해당 주문내역이 없습니다"));
        orders.setOrderStatus(OrderStatus.주문취소);
    }
}
