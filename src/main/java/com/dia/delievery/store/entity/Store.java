package com.dia.delievery.store.entity;

import com.dia.delievery.product.entity.Product;
import com.dia.delievery.review.entity.Review;
import com.dia.delievery.user.entity.User;
import com.dia.delievery.userscrapstore.entity.UserScrapStore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Store {
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
    private String Category;

    @OneToMany(mappedBy = "store")
    private List<Product> productList = new ArrayList<>();
    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "store")
    private List<UserScrapStore> userScrapStoreList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
