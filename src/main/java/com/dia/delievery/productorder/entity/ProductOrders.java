package com.dia.delievery.productorder.entity;

import com.dia.delievery.order.entity.Orders;
import com.dia.delievery.product.entity.Products;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ProductOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;
}
