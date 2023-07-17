package com.dia.delievery.product.entity;

import com.dia.delievery.store.entity.Stores;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String link;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Stores stores;
}