package com.dia.delivery.order.entity;

import com.dia.delivery.common.entity.Timestamped;
import com.dia.delivery.productorder.entity.ProductOrders;
import com.dia.delivery.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer orderNum;

    @Column(nullable = false)
    private String orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Users users;

    @OneToMany(mappedBy = "orders")
    private List<ProductOrders> productOrdersList = new ArrayList<>();
}