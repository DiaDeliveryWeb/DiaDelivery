package com.dia.delivery.store.service;

import com.dia.delivery.product.dto.ProductRequestDto;
import com.dia.delivery.product.entity.Products;
import com.dia.delivery.product.repository.ProductRepository;
import com.dia.delivery.store.dto.StoreDetailResponseDto;
import com.dia.delivery.store.dto.StoreRequestDto;
import com.dia.delivery.store.dto.StoreResponseDto;
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.store.repository.StoreRepository;
import com.dia.delivery.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    public List<StoreResponseDto> findAll() {
        return storeRepository.findAll().stream().map(StoreResponseDto::new).toList();
    }

    public StoreResponseDto save(Users users, StoreRequestDto requestDto) {
        Stores stores = new Stores(requestDto);
        storeRepository.save(stores);
        for(ProductRequestDto productRequestDto : requestDto.getRequestDtoList()) {
            Products products = new Products(productRequestDto, stores);
            productRepository.save(products);
        }
        return new StoreResponseDto(stores);
    }


    public StoreDetailResponseDto findOne(String name) {
        return new StoreDetailResponseDto(storeRepository.findByName(name));
    }

    public void delete(Users users, String name) {
        storeRepository.deleteByName(name);
    }

    public List<StoreResponseDto> findAllByCategory(String name) {
        return storeRepository.findAllByCategory(name).stream().map(StoreResponseDto::new).toList();
    }
}
