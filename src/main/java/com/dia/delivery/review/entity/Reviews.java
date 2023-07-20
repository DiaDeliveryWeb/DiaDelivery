package com.dia.delivery.review.entity;


import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.review.dto.ReviewRequestDto;
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.user.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
    @Column
    private String imageUrl;
    @Column
    private double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    private Stores stores;

    @OneToOne
    private Orders orders;

    public Reviews(ReviewRequestDto requestDto, Users users, Stores stores, Orders orders){
        this.content = requestDto.getContent();
        this.imageUrl = requestDto.getImageUrl();
        this.rate = requestDto.getRate();
        this.users = users;
        this.stores = stores;
        this.orders = orders;
    }

    public void update(ReviewRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.imageUrl = requestDto.getImageUrl();
        this.rate = requestDto.getRate();
    }
}
