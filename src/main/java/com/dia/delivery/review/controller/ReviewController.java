package com.dia.delivery.review.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.image.ImageUploader;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.review.dto.ReviewRequestDto;
import com.dia.delivery.review.dto.ReviewResponseDto;
import com.dia.delivery.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ImageUploader imageUploader;

    @PostMapping(value = "/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ReviewResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam Long orderId,
                                  @RequestPart ReviewRequestDto requestDto,
                                  @RequestPart MultipartFile image) throws IOException {
        return reviewService.save(userDetails.getUser(), orderId, requestDto, image);
    }

    @PutMapping(value = "/reviews", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ReviewResponseDto update(@AuthenticationPrincipal UserDetailsImpl userDetails,
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