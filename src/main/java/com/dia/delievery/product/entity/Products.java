package com.dia.delievery.product.entity;

import com.dia.delievery.store.entity.Stores;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Stores stores;

    public Products (String productName, int price, String description) {
        this.productName = productName;
        this.price = price;
        this.description = description;
    }
}