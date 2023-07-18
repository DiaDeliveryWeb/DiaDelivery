package com.dia.delievery.review.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.common.image.ImageUploader;
import com.dia.delievery.common.security.UserDetailsImpl;
import com.dia.delievery.review.dto.ReviewRequestDto;
import com.dia.delievery.review.dto.ReviewsResponeDto;
import com.dia.delievery.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;
    private final ImageUploader imageUploader;

    @PostMapping(value = "/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ReviewsResponeDto save(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam Long orderId,
                                  @RequestPart ReviewRequestDto requestDto,
                                  @RequestPart MultipartFile image) throws IOException {
        return reviewService.save(userDetails.getUser(), orderId, requestDto, image);
    }

    @PutMapping(value = "/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ReviewsResponeDto update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestParam Long orderId,
                                    @RequestPart ReviewRequestDto requestDto,
                                    @RequestPart MultipartFile image) throws IOException {
        return reviewService.update(userDetails.getUser(), orderId, requestDto, image);
    }

    @DeleteMapping("/reviews")
    public ResponseEntity<ApiResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long orderId){
        reviewService.delete(userDetails.getUser(), orderId);
        return ResponseEntity.ok(new ApiResponseDto("리뷰 삭제 완료", 200));
    }
}
