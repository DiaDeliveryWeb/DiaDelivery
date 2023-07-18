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

    @OneToMany(mappedBy = "stores")
    private List<Products> productsList = new ArrayList<>();
    @OneToMany(mappedBy = "stores")
    private List<Reviews> reviewsList = new ArrayList<>();
    @OneToMany(mappedBy = "stores")
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
    }

    public void update(StoreUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        this.introduction = requestDto.getIntroduction();
        this.imageUrl = requestDto.getImageUrl();
        this.category = requestDto.getCategory();
    }

    public void addOneProductList(StoreRequestDto requestDto) {
        ProductRequestDto req = requestDto.getProductList().get(0);
        String productName = req.getProductName();
        int price = req.getPrice();
        String description = req.getDescription();
        Products product = new Products(productName, price, description);
        this.productsList.add(product);
    }
}
