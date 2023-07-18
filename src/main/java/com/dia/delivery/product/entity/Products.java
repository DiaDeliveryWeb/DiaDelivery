package com.dia.delivery.product.entity;

import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.store.entity.Stores;
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

//    @Column(nullable = false)
//    private String link;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Stores stores;

    public Products(ProductRequestDto requestDto, Stores stores){
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
        this.stores = stores;
    }

    public Products(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
    }

    public void update(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
    }
}