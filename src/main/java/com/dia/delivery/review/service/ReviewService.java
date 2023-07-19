package com.dia.delivery.review.service;

import com.dia.delivery.common.image.ImageUploader;
import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.order.repository.OrderRepository;
import com.dia.delivery.review.dto.ReviewRequestDto;
import com.dia.delivery.review.dto.ReviewResponseDto;
import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.review.repository.ReviewRepository;
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.user.entity.Users;
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

    public ReviewResponseDto save(Users user, Long orderId, ReviewRequestDto requestDto, MultipartFile image) throws IOException {
        if (image != null) {
            String imageUrl = imageUploader.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        if (!orders.getUsers().getId().equals(user.getId())) throw new IllegalArgumentException("주문하신 회원이 아닙니다.");
        if (orders.getReviews() != null) throw new IllegalArgumentException("이미 리뷰를 작성한 주문입니다.");
        Stores stores = orders.getProductOrdersList().get(0).getProducts().getStores();
        return new ReviewResponseDto(reviewRepository.save(new Reviews(requestDto, user, stores, orders)));
    }
    public ReviewResponseDto update(Users user, Long orderId, ReviewRequestDto requestDto, MultipartFile image) throws IOException {
        if (image != null){
            String imageUrl = imageUploader.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        if (!orders.getUsers().getId().equals(user.getId()))  throw new IllegalArgumentException("주문하신 회원이 아닙니다.");
        Reviews reviews = orders.getReviews();
        reviews.update(requestDto);
        return new ReviewResponseDto(reviews);
    }

    public void delete(Users user, Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        if (!orders.getUsers().getId().equals(user.getId()))  throw new IllegalArgumentException("주문하신 회원이 아닙니다.");
        orders.deleteReview();
    }
}