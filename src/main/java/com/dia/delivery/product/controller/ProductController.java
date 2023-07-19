package com.dia.delivery.product.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.product.dto.ProductRequestListDto;
import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping("/products")
    public List<ProductResponseDto> addProducts(@RequestParam Long storeId,
                                                @RequestBody ProductRequestListDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
       return productService.addProducts(storeId,requestDto,userDetails.getUser());
    }

    @PutMapping("/products")
    public ProductResponseDto updateProduct(@RequestParam Long productId,
                                            @RequestBody ProductRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.updateProduct(productId,requestDto,userDetails.getUser());
    }

    @DeleteMapping("/products")
    public ResponseEntity<ApiResponseDto> deleteProduct(@RequestParam Long productId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.deleteProduct(productId,userDetails.getUser());
    }
}
