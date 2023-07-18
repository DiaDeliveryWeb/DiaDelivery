package com.dia.delievery.store.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.common.security.UserDetailsImpl;
import com.dia.delievery.store.dto.StoreDetailResponseDto;
import com.dia.delievery.store.dto.StoreRequestDto;
import com.dia.delievery.store.dto.StoreResponseDto;
import com.dia.delievery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public StoreResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody StoreRequestDto requestDto){
        return storeService.save(userDetails.getUser(), requestDto);
    }

    @GetMapping("/stores")
    public List<StoreResponseDto> findAll(@RequestParam(required = false) String name){
        if (name == null)
            return storeService.findAll();
        return storeService.findAllByCategory(name);
    }

    @GetMapping("/store")
    public StoreDetailResponseDto findOne(@RequestParam String name){
        return storeService.findOne(name);
    }

    @DeleteMapping("/store")
    public ResponseEntity<ApiResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String name){
        storeService.delete(userDetails.getUser(), name);
        return ResponseEntity.ok(new ApiResponseDto("가게 삭제 완료", 200));
    }
}
