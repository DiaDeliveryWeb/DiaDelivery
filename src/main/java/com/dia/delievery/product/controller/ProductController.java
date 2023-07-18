package com.dia.delievery.product.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.common.security.UserDetailsImpl;
import com.dia.delievery.product.dto.ProductRequestDto;
import com.dia.delievery.product.dto.ProductResponseDto;
import com.dia.delievery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long storeId, @RequestBody ProductRequestDto requestDto){
        return productService.save(userDetails.getUser(), storeId, requestDto);
    }
    @PutMapping("/products")
    public ProductResponseDto update(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long productId, @RequestBody ProductRequestDto requestDto){
        return productService.update(userDetails.getUser(), productId, requestDto);
    }
    @DeleteMapping("/products")
    public ResponseEntity<ApiResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long productId){
        productService.delete(userDetails.getUser(), productId);
        return ResponseEntity.ok(new ApiResponseDto("상품 삭제 완료", 200));
    }
}
