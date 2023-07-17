package com.dia.delievery.productorder.entity;

import com.dia.delievery.order.entity.Order;
import com.dia.delievery.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product productList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order orderList;
}
