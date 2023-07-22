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
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        if(!user.getRole().equals(UserRoleEnum.USER)){
            throw new IllegalArgumentException("고객만 주문이 가능합니다.");
        }
        Stores stores = productRepository.findById(requestDto.getProductList().get(0))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품이 있습니다.")).getStores();
        Users owner = stores.getUsers();
        Orders orders = new Orders(user, owner);

        List<Products> products = new ArrayList<>();
        for (Long productId : requestDto.getProductList()) {
            Products product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품이 있습니다."));
            products.add(product);
            productOrderRepository.save(new ProductOrders(orders, product));
        }

        return new OrderResponseDto(stores.getName(), products, user.getUsername(), orders.getOrderStatus());
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
        if (!orders.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("주문하신 회원이 아닙니다.");
        }
        orders.setOrderStatus(OrderStatus.주문완료);
    }

    public void cancel(Users user, String orderNum) {
        Orders orders = orderRepository.findByOrderNum(orderNum).orElseThrow(()->new IllegalArgumentException("해당 주문내역이 없습니다"));
        if (!orders.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("주문하신 회원이 아닙니다.");
        }
        orders.setOrderStatus(OrderStatus.주문취소);
    }

    public List<OrderResponseDto> findAll(Users user) {
        if(user.getRole().equals(UserRoleEnum.USER)) {
            return orderRepository.findAllByUser(user).stream().map(OrderResponseDto::new).toList();
        }
        return orderRepository.findAllByOwner(user).stream().map(OrderResponseDto::new).toList();
    }

    public OrderResponseDto findOne(Users user, String orderNum) {
        Orders orders = orderRepository.findByOrderNum(orderNum).orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));
        System.out.println(orders.getOrderNum());
        if (!(orders.getUser().getId().equals(user.getId()) || orders.getOwner().getId().equals(user.getId()))){
            throw new IllegalArgumentException("해당 주문의 조회권한이 없습니다.");
        }
        return new OrderResponseDto(orders);
    }
}