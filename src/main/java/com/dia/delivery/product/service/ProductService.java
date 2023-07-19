package com.dia.delivery.product.service;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.product.dto.ProductRequestListDto;
import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.product.dto.ProductResponseDto;
import com.dia.delivery.product.entity.Products;
import com.dia.delivery.product.repository.ProductRepository;
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.store.repository.StoreRepository;
import com.dia.delivery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductResponseDto> addProducts(Long storeId, ProductRequestListDto requestDto, Users user) {
        Stores store=storeRepository.findById(storeId).orElseThrow(()-> new IllegalArgumentException("음식점이 존재하지 않습니다."));

        if(user.getRole().equals("USER")){
            throw new IllegalArgumentException("상품 등록 권한이 없습니다.");
        }
        List<ProductResponseDto> productResponseDtoList=new ArrayList<>();

        for(ProductRequestDto oneProduct : requestDto.getProductList()){
            Products product = new Products(oneProduct,store);
            productResponseDtoList.add(new ProductResponseDto(productRepository.save(product)));
        }

        return productResponseDtoList;
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto, Users user) {
        Products product= productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("상품이 존재하지 않습니다"));

        if(user.getRole().equals("USER")){
            throw new IllegalArgumentException("상품 수정 권한이 없습니다.");
        }

        product.update(requestDto);

        return new ProductResponseDto(product);
    }
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteProduct(Long productId, Users user) {
        Products product=productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("상품이 존재하지 않습니다"));

        if(user.getRole().equals("USER")){
            throw new IllegalArgumentException("상품 삭제 권한이 없습니다.");
        }
        ApiResponseDto apiResponseDto = new ApiResponseDto("상품 삭제 완료", HttpStatus.OK.value());
        productRepository.delete(product);

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
