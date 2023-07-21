package com.dia.delivery.user.entity;

import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.userscrapstore.entity.UserScrapStore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String passwordDecoded;

    @Column
    private String password2;

    @Column
    private String password3;

    @Column(nullable = false, unique = true)
    private String email;

  
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private String imageUrl;

    @Column
    private String introduction;

    @OneToMany(mappedBy = "users")
    private List<Stores> storesList = new ArrayList<>();
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Reviews> reviewsList = new ArrayList<>();
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<UserScrapStore> userScrapStoreList = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Orders> ownerOrders = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Orders> userOrders = new ArrayList<>();


    public Users(String username, String password, String passwordDecoded, String password2, String password3, String email, UserRoleEnum role) {
        this.username=username;
        this.password=password;
        this.passwordDecoded = passwordDecoded;
        this.password2=password2;
        this.password3=password3;
        this.email=email;
        this.role=role;
    }
}
