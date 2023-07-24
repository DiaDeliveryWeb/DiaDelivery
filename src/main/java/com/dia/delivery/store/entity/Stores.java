package com.dia.delivery.store.entity;

import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.product.entity.Products;
import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.store.dto.StoreRequestDto;
import com.dia.delivery.store.dto.StoreUpdateRequestDto;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.userscrapstore.entity.UserScrapStore;
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

    @Column(unique = true)
    private String name;
    @Column
    private String introduction;
    @Column
    private String imageUrl;
    @Column
    private String category;

    @OneToMany(mappedBy = "stores", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Products> productsList = new ArrayList<>();
  
    @OneToMany(mappedBy = "stores", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Reviews> reviewsList = new ArrayList<>();
  
    @OneToMany(mappedBy = "stores", orphanRemoval = true)
    private List<UserScrapStore> userScrapStoreList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    public Stores(StoreRequestDto requestDto, Users user) {
        this.name = requestDto.getName();
        this.introduction = requestDto.getIntroduction();
        this.imageUrl = requestDto.getImageUrl();
        this.category = requestDto.getCategory();
        this.users = user;
        this.productsList = requestDto.getProductList().stream().map((request) -> new Products(request, this)).toList();
    }

    public void update(StoreUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        this.introduction = requestDto.getIntroduction();
        this.imageUrl = requestDto.getImageUrl();
        this.category = requestDto.getCategory();
    }
}
