package com.dia.delievery.product.entity;

import com.dia.delievery.product.dto.ProductRequestDto;
import com.dia.delievery.store.entity.Stores;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    public Products(ProductRequestDto requestDto,Stores stores) {
        this.productName = requestDto.getProductName();
        this.imageUrl = requestDto.getImageUrl();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
        this.stores = stores;
    }

   public void update(ProductRequestDto requestDto){
       this.productName = requestDto.getProductName();
       this.imageUrl = requestDto.getImageUrl();
       this.price = requestDto.getPrice();
       this.description = requestDto.getDescription();
   }


}