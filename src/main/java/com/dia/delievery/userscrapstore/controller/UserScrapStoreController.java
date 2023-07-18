package com.dia.delievery.userscrapstore.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.common.security.UserDetailsImpl;
import com.dia.delievery.userscrapstore.service.UserScrapStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserScrapStoreController {
    private final UserScrapStoreService userScrapStoreService;

    @PostMapping("/scrap")
    public ResponseEntity<ApiResponseDto> save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long storeId){
        userScrapStoreService.save(userDetails.getUser(), storeId);
        return ResponseEntity.ok(new ApiResponseDto("스크랩 완료", 200));
    }
}
