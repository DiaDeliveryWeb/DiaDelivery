package com.dia.delievery.product.service;

import com.dia.delievery.product.dto.ProductRequestDto;
import com.dia.delievery.product.dto.ProductResponseDto;
import com.dia.delievery.product.entity.Products;
import com.dia.delievery.product.repository.ProductRepository;
import com.dia.delievery.store.entity.Stores;
import com.dia.delievery.store.repository.StoreRepository;
import com.dia.delievery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto addProducts(Long storeId, ProductRequestDto requestDto, Users user) {
        Stores store=storeRepository.findById(storeId).orElseThrow(()-> new IllegalArgumentException("음식점이 존재하지 않습니다."));

        if(user.getRole().equals("USER")){
            throw new IllegalArgumentException("상품 등록 권한이 없습니다.");
        }
        Products product= new Products(requestDto,store);

        productRepository.save(product);

        return new ProductResponseDto(product);
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
    public void deleteProduct(Long productId, Users user) {
        Products product=productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("상품이 존재하지 않습니다"));

        if(user.getRole().equals("USER")){
            throw new IllegalArgumentException("상품 삭제 권한이 없습니다.");
        }
        productRepository.delete(product);
    }
}
