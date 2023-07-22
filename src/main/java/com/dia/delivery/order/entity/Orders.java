package com.dia.delivery.order.entity;

import com.dia.delivery.common.entity.Timestamped;
import com.dia.delivery.order.OrderStatus;
import com.dia.delivery.productorder.entity.ProductOrders;
import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static com.dia.delivery.order.OrderStatus.주문생성;

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
    private Users owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Users user;

    @OneToMany(mappedBy = "orders")
    private List<ProductOrders> productOrdersList = new ArrayList<>();

    @OneToOne(mappedBy = "orders", orphanRemoval = true)
    private Reviews reviews;

    public Orders(Users user, Users owner){
        this.user = user;
        this.owner = owner;
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