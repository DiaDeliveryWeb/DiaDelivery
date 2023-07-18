package com.dia.delievery.store.entity;

import com.dia.delievery.product.entity.Products;
import com.dia.delievery.review.entity.Reviews;
import com.dia.delievery.store.dto.StoreRequestDto;
import com.dia.delievery.user.entity.Users;
import com.dia.delievery.userscrapstore.entity.UserScrapStore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Stores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String introduction;
    @Column
    private String imageUrl;
    @Column
    private String category;

    @OneToMany(mappedBy = "stores", orphanRemoval = true)
    private List<Products> productsList = new ArrayList<>();
    @OneToMany(mappedBy = "stores")
    private List<Reviews> reviewsList = new ArrayList<>();
    @OneToMany(mappedBy = "stores")
    private List<UserScrapStore> userScrapStoreList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    public Stores(StoreRequestDto requestDto){
        this.name = requestDto.getName();
        this.introduction = requestDto.getIntroduction();
        this.imageUrl = requestDto.getImageUrl();
        this.category = requestDto.getCategory();
        this.productsList = requestDto.getRequestDtoList().stream().map(Products::new).toList();
    }
}
