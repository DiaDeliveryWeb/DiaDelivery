package com.dia.delivery.admin.controller;

import com.dia.delivery.admin.dto.*;
import com.dia.delivery.admin.service.AdminService;
import com.dia.delivery.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @PostMapping("/office")
    public ResponseEntity<ApiResponseDto> checkToken(@RequestBody TokenRequestDto requestDto) {
        return adminService.checkToken(requestDto.getToken());
    }

    @GetMapping("/office/user")
    public List<OfficeUserResponseDto> getUserName() {
        return adminService.getUser();
    }

    @GetMapping("/office/order")
    public List<OfficeOrderResponseDto> getOrder() {
        return adminService.getOrder();
    }

    @GetMapping("/office/review")
    public List<OfficeReviewResponseDto> getReview() {
        return adminService.getReview();
    }

    @PutMapping("/office/block")
    public void blockUser(@RequestBody OfficeBlockRequestDto requestDto) {
        System.out.println("들어옴");
        System.out.println(requestDto.getUsername());
        adminService.blockUser(requestDto.getUsername());
    }
}
