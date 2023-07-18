package com.dia.delievery.store.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.common.security.UserDetailsImpl;
import com.dia.delievery.store.dto.*;
import com.dia.delievery.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class StoreController {

    public final StoreService storeService;

    // 가게 등록
    @PostMapping("/stores")
    public ResponseEntity<StoreCreateResponseDto> createStore(@RequestBody StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.createStore(requestDto, userDetails.getUser());
    }

    // 가게 전체 조회
    @GetMapping("/stores")
    public List<StoreResponseDto> getStores() {
        return storeService.getStores();
    }

    // 가게 상세 조회
    @GetMapping("/store")
    public ResponseEntity<StoreOneResponseDto> getStore(@RequestParam String name) {
        return storeService.getStore(name);
    }

    // 가게 수정
    @PutMapping("/stores")
    public ResponseEntity<StoreResponseDto> updateStore(@RequestParam String name, @RequestBody StoreUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.updateStore(name, requestDto, userDetails.getUser());
    }

    // 가게 삭제
    @DeleteMapping("/stores")
    public ResponseEntity<ApiResponseDto> deleteStore(@RequestParam String name, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.deleteStore(name, userDetails.getUser());
    }

    // 카테고리별 가게 조회
    @GetMapping("/stores/category")
    public List<StoreResponseDto> getStoresByCategory(@RequestParam String name) {
        return storeService.getStoresByCategory(name);
    }
}
