package com.dia.delievery.product.entity;

import com.dia.delievery.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productname;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String descrption;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}