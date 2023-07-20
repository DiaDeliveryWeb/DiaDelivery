package com.dia.delivery.product.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.product.dto.ProductRequestListDto;
import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/products", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<ProductResponseDto> addProducts(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam Long storeId,
                                  @RequestPart ProductRequestListDto requestDto,
                                  @RequestPart List<MultipartFile> image) throws IOException {
        return productService.addProducts(storeId,requestDto,userDetails.getUser(),image);
    }

    @PutMapping(value = "/products", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProductResponseDto updateProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestParam Long productId,
                                            @RequestPart ProductRequestDto requestDto,
                                            @RequestPart MultipartFile image)throws IOException{
        return productService.updateProduct(productId,requestDto,userDetails.getUser(),image);
    }

    @DeleteMapping("/products")
    public ResponseEntity<ApiResponseDto> deleteProduct(@RequestParam Long productId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return productService.deleteProduct(productId,userDetails.getUser());
    }
}
