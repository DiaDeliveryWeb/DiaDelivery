package com.dia.delivery.admin.service;

import com.dia.delivery.admin.dto.OfficeOrderResponseDto;
import com.dia.delivery.admin.dto.OfficeReviewResponseDto;
import com.dia.delivery.admin.dto.OfficeUserResponseDto;
import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.order.entity.Orders;
import com.dia.delivery.order.repository.OrderRepository;
import com.dia.delivery.review.dto.ReviewResponseDto;
import com.dia.delivery.review.entity.Reviews;
import com.dia.delivery.review.repository.ReviewRepository;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "2222";
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public ResponseEntity<ApiResponseDto> checkToken(String token) {
        if (!token.equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        ApiResponseDto apiResponseDto = new ApiResponseDto("권한 확인 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    public List<OfficeUserResponseDto> getUser() {
        List<Users> userList = userRepository.findAll();
        return userList.stream().map(users -> new OfficeUserResponseDto(users.getUsername())).toList();
    }

    public List<OfficeOrderResponseDto> getOrder() {
        List<Orders> orderList = orderRepository.findAll();
        return orderList.stream().map(orders -> new OfficeOrderResponseDto(orders.getOrderNum(), orders.getOrderStatus())).toList();
    }

    public List<OfficeReviewResponseDto> getReview() {
        List<Reviews> reviewList = reviewRepository.findAll();
        return reviewList.stream().map(reviews -> new OfficeReviewResponseDto(reviews.getUsers().getUsername(), reviews.getOrders().getOrderNum(),
                reviews.getContent(), reviews.getRate())).toList();
    }
}
