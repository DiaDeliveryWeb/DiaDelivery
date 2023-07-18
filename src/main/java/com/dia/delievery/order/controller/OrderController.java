package com.dia.delievery.order.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.common.security.UserDetailsImpl;
import com.dia.delievery.order.dto.OrderRequestDto;
import com.dia.delievery.order.dto.OrderResponseDto;
import com.dia.delievery.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/orders")
    public OrderResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody OrderRequestDto requestDto){
        return orderService.save(userDetails.getUser(), requestDto);
    }

    @PutMapping("/orders")
    public ResponseEntity<ApiResponseDto> accept(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String orderNum){
        orderService.accept(userDetails.getUser(), orderNum);
        return ResponseEntity.ok(new ApiResponseDto("주문 수락 완료", 200));
    }

    @DeleteMapping("/orders")
    public ResponseEntity<ApiResponseDto> cancel(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String orderNum){
        orderService.cancel(userDetails.getUser(), orderNum);
        return ResponseEntity.ok(new ApiResponseDto("주문 취소 완료", 200));
    }
}
