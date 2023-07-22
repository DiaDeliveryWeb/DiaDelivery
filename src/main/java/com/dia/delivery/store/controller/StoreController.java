package com.dia.delivery.store.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.store.dto.*;
import com.dia.delivery.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
public class StoreController {

    public final StoreService storeService;


    // 가게 등록
    @PostMapping(value = "/stores", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<StoreCreateResponseDto> createStore(@RequestPart StoreRequestDto requestDto,
                                                              @RequestPart(required = false) List<MultipartFile> productsImage,
                                                              @RequestPart(required = false) MultipartFile storeImage,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException
    {
        return storeService.createStore(requestDto, storeImage, productsImage, userDetails.getUser());
    }

    // 가게 전체 조회
    @GetMapping("/stores")
    public List<StoreResponseDto> getStores() {
        return storeService.getStores();
    }


    // 사용자별 가게 전체 조회
    @GetMapping("/mystores")
    public List<StoreResponseDto> getMyStores(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.getMyStores(userDetails.getUser());
    }

    // 가게 상세 조회
    @GetMapping("/store")
    public ResponseEntity<StoreOneResponseDto> getStore(@RequestParam String name) {
        return storeService.getStore(name);
    }

    // 가게 수정
    @PutMapping(value = "/stores", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<StoreResponseDto> updateStore(@RequestParam String name, @RequestPart StoreUpdateRequestDto requestDto, @RequestPart MultipartFile image, @AuthenticationPrincipal UserDetailsImpl userDetails) throws  IOException
    {
        return storeService.updateStore(name, requestDto, image, userDetails.getUser());
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
