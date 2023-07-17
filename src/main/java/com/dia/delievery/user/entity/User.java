package com.dia.delievery.user.entity;

import com.dia.delievery.order.entity.Order;
import com.dia.delievery.review.entity.Review;
import com.dia.delievery.store.entity.Store;
import com.dia.delievery.user.UserRoleEnum;
import com.dia.delievery.userscrapstore.entity.UserScrapStore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private int point;
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<Store> storeList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<UserScrapStore> userScrapStoreList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();
}
