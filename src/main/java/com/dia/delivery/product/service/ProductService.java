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
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final MessageSource messageSource;

    @Transactional
    public List<ProductResponseDto> addProducts(Long storeId, ProductRequestListDto requestDto, Users user) {
        Stores store=storeRepository.findById(storeId).orElseThrow(()-> new IllegalArgumentException(
                messageSource.getMessage(
                "not.found.store",
                null,
                "Not Found Store",
                Locale.getDefault()
        )));

        if(user.getRole().getAuthority().equals("ROLE_USER")){
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                    "not.have.ownerrole",
                    null,
                    "Not Have Role",
                    Locale.getDefault()
            ));
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

        if(user.getRole().getAuthority().equals("ROLE_USER")){
            throw new IllegalArgumentException(  messageSource.getMessage(
                    "not.have.ownerrole",
                    null,
                    "Not Have Role",
                    Locale.getDefault()
            ));
        }

        product.update(requestDto);

        return new ProductResponseDto(product);
    }
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteProduct(Long productId, Users user) {
        Products product=productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException(
                messageSource.getMessage(
                        "not.found.product",
                        null,
                        "Not Found Product",
                        Locale.getDefault()
                )
        ));

        if(user.getRole().getAuthority().equals("ROLE_USER")){
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                    "not.have.deleterole",
                    null,
                    "Not Have Role",
                    Locale.getDefault()
            ));
        }
        ApiResponseDto apiResponseDto = new ApiResponseDto("상품 삭제 완료", HttpStatus.OK.value());
        productRepository.delete(product);

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
