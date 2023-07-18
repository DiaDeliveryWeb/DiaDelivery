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
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductResponseDto save(Users user, Long storeId, ProductRequestDto requestDto) {
        Stores stores = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));
        return new ProductResponseDto(productRepository.save(new Products(requestDto, stores)));
    }

    public ProductResponseDto update(Users user, Long productId, ProductRequestDto requestDto) {
        Products products = productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        products.update(requestDto);
        return new ProductResponseDto(products);
    }

    public void delete(Users user, Long productId) {
        productRepository.deleteById(productId);
    }
}
