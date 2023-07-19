package com.dia.delivery.product.controller;

import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping("/products")
    public void addProducts(@RequestParam Long storeId,
                            @RequestBody List<ProductRequestDto> requestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        productService.addProducts(storeId,requestDto,userDetails.getUser());
    }

    @PutMapping("/products")
    public void updateProduct(@RequestParam Long productId,
                            @RequestBody ProductRequestDto requestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        productService.updateProduct(productId,requestDto,userDetails.getUser());
    }

    @DeleteMapping("/products")
    public void deleteProduct(@RequestParam Long productId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        productService.deleteProduct(productId,userDetails.getUser());
    }
}