package com.dia.delievery.order.entity;

import com.dia.delievery.common.entity.Timestamped;
import com.dia.delievery.order.OrderStatus;
import com.dia.delievery.product.entity.Products;
import com.dia.delievery.productorder.entity.ProductOrders;
import com.dia.delievery.review.entity.Reviews;
import com.dia.delievery.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.dia.delievery.order.OrderStatus.주문생성;
import static com.dia.delievery.order.OrderStatus.주문완료;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNum;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Users users;

    @OneToMany(mappedBy = "orders")
    private List<ProductOrders> productOrdersList = new ArrayList<>();

    @OneToOne(mappedBy = "orders", orphanRemoval = true)
    private Reviews reviews;

    public Orders(Users users){
        this.users = users;
        this.orderStatus = 주문생성;
        this.orderNum = UUID.randomUUID().toString();
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void deleteReview() {
        this.reviews = null;
    }
}