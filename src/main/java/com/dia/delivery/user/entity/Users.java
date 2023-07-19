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

    @Column
    private String username;

    @Column //가장 최신 비밀번호
    private String password;
    @Column
    private String password2;
    @Column //가장 나중 비밀번호
    private String password3;
    @Column
    private String email;
    @Column
    private int point;
  
    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "users")
    private List<Stores> storesList = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    private List<Reviews> reviewsList = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    private List<UserScrapStore> userScrapStoreList = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    private List<Orders> ordersList = new ArrayList<>();


    public Users(String username, String password, String password2, String password3, String email, UserRoleEnum role) {
        this.username=username;
        this.password=password;
        this.password2=password2;
        this.password3=password3;
        this.email=email;
        this.role=role;
    }
}
