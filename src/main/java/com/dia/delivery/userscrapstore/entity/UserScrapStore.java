package com.dia.delivery.userscrapstore.entity;

import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserScrapStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    private Stores stores;
}
