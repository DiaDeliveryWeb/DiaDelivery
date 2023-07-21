package com.dia.delivery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {
    private String imageUrl;
    private String introduction;
}
