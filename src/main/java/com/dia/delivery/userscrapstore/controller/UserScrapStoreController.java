package com.dia.delivery.userscrapstore.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.userscrapstore.service.UserScrapStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserScrapStoreController {
    private final UserScrapStoreService userScrapStoreService;

    @PostMapping("/scrap")
    public ResponseEntity<ApiResponseDto> save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long storeId){
        userScrapStoreService.save(userDetails.getUser(), storeId);
        return ResponseEntity.ok(new ApiResponseDto("스크랩 완료", 200));
    }
    @DeleteMapping("/scrap")
    public ResponseEntity<ApiResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long storeId){
        userScrapStoreService.delete(userDetails.getUser(), storeId);
        return ResponseEntity.ok(new ApiResponseDto("스크랩 취소", 200));
    }
}