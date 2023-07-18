package com.dia.delievery.review.service;

import com.dia.delievery.common.image.ImageUploader;
import com.dia.delievery.order.entity.Orders;
import com.dia.delievery.order.repository.OrderRepository;
import com.dia.delievery.review.dto.ReviewRequestDto;
import com.dia.delievery.review.dto.ReviewsResponeDto;
import com.dia.delievery.review.entity.Reviews;
import com.dia.delievery.review.repository.ReviewRepository;
import com.dia.delievery.store.entity.Stores;
import com.dia.delievery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ImageUploader imageUploader;

    public ReviewsResponeDto save(Users user, Long orderId, ReviewRequestDto requestDto, MultipartFile image) throws IOException {
        if (image != null){
            String imageUrl = imageUploader.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        Stores stores = orders.getProductOrdersList().get(0).getProducts().getStores();
        return new ReviewsResponeDto(reviewRepository.save(new Reviews(requestDto, user, stores, orders)));
    }

    public ReviewsResponeDto update(Users user, Long orderId, ReviewRequestDto requestDto, MultipartFile image) throws IOException {
        if (image != null){
            String imageUrl = imageUploader.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        Reviews reviews = orders.getReviews();
        reviews.update(requestDto);
        return new ReviewsResponeDto(reviews);
    }

    public void delete(Users user, Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        orders.deleteReview();
    }
}
