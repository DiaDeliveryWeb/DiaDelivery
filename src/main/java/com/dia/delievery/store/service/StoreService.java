package com.dia.delievery.store.service;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.store.dto.*;
import com.dia.delievery.store.entity.Stores;
import com.dia.delievery.store.repository.StoreRepository;
import com.dia.delievery.user.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Component
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional
    public ResponseEntity<StoreCreateResponseDto> createStore(StoreRequestDto requestDto, Users user) {
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException("소유자, 관리자만 가게를 등록할 수 있습니다.");
        }
        Stores store = new Stores(requestDto, user);
        store.addOneProductList(requestDto);
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
    public ResponseEntity<StoreResponseDto> updateStore(String name, StoreUpdateRequestDto requestDto, Users user) {
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException("소유자, 관리자만 가게를 수정할 수 있습니다.");
        }
        Stores store = findStore(name);
        store.update(requestDto);
        StoreResponseDto storeResponseDto = new StoreResponseDto(store);
        return new ResponseEntity<>(storeResponseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> deleteStore(String name, Users user) {
        if (!user.getRole().getAuthority().equals("ROLE_ADMIN") && !user.getRole().getAuthority().equals("ROLE_OWNER")) {
            throw new IllegalArgumentException("소유자, 관리자만 가게를 삭제할 수 있습니다.");
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

    public Stores findStore(String name) {
        Stores store = storeRepository.findByName(name);
        if (store == null) {
            throw new IllegalArgumentException("해당하는 가게가 없습니다.");
        }
        return store;
    }
}
