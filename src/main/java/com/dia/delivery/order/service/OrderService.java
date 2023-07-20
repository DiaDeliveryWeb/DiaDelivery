package com.dia.delivery.order.service;

import com.dia.delivery.order.OrderStatus;
import com.dia.delivery.order.dto.OrderRequestDto;
import com.dia.delivery.order.dto.OrderResponseDto;
import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.order.repository.OrderRepository;
import com.dia.delivery.product.entity.Products;
import com.dia.delivery.product.repository.ProductRepository;
import com.dia.delivery.productorder.entity.ProductOrders;
import com.dia.delivery.productorder.repository.ProductOrderRepository;
import com.dia.delivery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final MessageSource messageSource;

    public OrderResponseDto save(Users user, OrderRequestDto requestDto) {
        List<Products> products = productRepository.findAllByIdIn(requestDto.getProductList());
        Orders orders = new Orders(user);
        for (Products product : products) {
            productOrderRepository.save(new ProductOrders(orders, product));
        }
        return new OrderResponseDto(products.get(0).getStores().getName(), products, user.getUsername(), orders.getOrderStatus());
    }

    public void accept(Users user, String orderNum) {
        Orders orders = orderRepository.findByOrderNum(orderNum).orElseThrow(()->new IllegalArgumentException(
                messageSource.getMessage(
                        "not.correct.admintoken",
                        null,
                        "Uncorrect AdminToken",
                        Locale.getDefault()
                )
        ));
        orders.setOrderStatus(OrderStatus.주문완료);
    }

    public void cancel(Users user, String orderNum) {
        Orders orders = orderRepository.findByOrderNum(orderNum).orElseThrow(()->new IllegalArgumentException("해당 주문내역이 없습니다"));
        orders.setOrderStatus(OrderStatus.주문취소);
    }

    public List<OrderResponseDto> findAll(Users user) {
        return orderRepository.findAllByUsers(user).stream().map(OrderResponseDto::new).toList();
    }
}