package com.dia.delivery.store.service;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.image.ImageUploader;
import com.dia.delivery.store.dto.*;
import com.dia.delivery.store.entity.Stores;
import com.dia.delivery.store.repository.StoreRepository;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import com.dia.delivery.userscrapstore.entity.UserScrapStore;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Component
public class StoreService {
    private final StoreRepository storeRepository;
    private final ImageUploader imageUploader;
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    @Transactional
    public ResponseEntity<StoreCreateResponseDto> createStore(StoreRequestDto requestDto, MultipartFile storeImage, List<MultipartFile> productsImage, Users user) throws IOException {
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException(
                             messageSource.getMessage(
                            "not.create.role",
                            null,
                            "Not Enroll Store",
                            Locale.getDefault()
                    ));
        }
        if (storeImage != null) {
            String imageUrl = imageUploader.upload(storeImage, "image");
            requestDto.setImageUrl(imageUrl);
        }
        if (productsImage != null){
            for (int i = 0; i<productsImage.size(); i++){
                String imageUrl = imageUploader.upload(productsImage.get(i), "image");
                requestDto.getProductList().get(i).setImageUrl(imageUrl);
            }
        }
        Stores store = new Stores(requestDto, user);
        System.out.println(store.getImageUrl());
//        store.addOneProductList(requestDto);
        storeRepository.save(store);
        StoreCreateResponseDto storeCreateResponseDto = new StoreCreateResponseDto(store);
        return new ResponseEntity<>(storeCreateResponseDto, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStores() {
        return storeRepository.findAll().stream().map(StoreResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<StoreOneResponseDto> getStore(String name) {
        Stores store = findStore(name);
        StoreOneResponseDto storeOneResponseDto = new StoreOneResponseDto(store);
        return new ResponseEntity<>(storeOneResponseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<StoreResponseDto> updateStore(String name, StoreUpdateRequestDto requestDto, MultipartFile image, Users user) throws IOException{
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException(
                            messageSource.getMessage(
                            "not.update.role",
                            null,
                            "Not Update Store",
                            Locale.getDefault()
                    ));
        }
        if (image != null) {
            String imageUrl = imageUploader.upload(image, "image");
            requestDto.setImageUrl(imageUrl);
        }
        Stores store = findStore(name);
        store.update(requestDto);
        StoreResponseDto storeResponseDto = new StoreResponseDto(store);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> deleteStore(String name, Users user) {
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException(
                            messageSource.getMessage(
                            "not.delete.role",
                            null,
                            "Not Enroll Store",
                            Locale.getDefault()
                    )
            );
        }
        Stores store = findStore(name);
        storeRepository.delete(store);
        ApiResponseDto apiResponseDto = new ApiResponseDto("가게 삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStoresByCategory(String name) {
        return storeRepository.findByCategory(name).stream().map(StoreResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public Stores findStore(String name) {
        Stores store = storeRepository.findByName(name);
        if (store == null) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                    "not.found.store",
                    null,
                    "Not Found Store",
                    Locale.getDefault()
            ));
        }
        return store;
    }

    // 나의 가게 조회
    @Transactional(readOnly = true)

    public List<StoreResponseDto> getMyStores(Users user) {
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "not.read.role",
                            null,
                            "Not Update Store",
                            Locale.getDefault()
                    ));
        }
        return storeRepository.findByUsersId(user.getId()).stream().map(StoreResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getScrapStores(Users user) {
        Users users = userRepository.findById(user.getId()).orElseThrow(()->new IllegalArgumentException("유저 정보가 없습니다."));
        return users.getUserScrapStoreList().stream().map(userScrapStore -> new StoreResponseDto(userScrapStore.getStores())).toList();
    }
}
