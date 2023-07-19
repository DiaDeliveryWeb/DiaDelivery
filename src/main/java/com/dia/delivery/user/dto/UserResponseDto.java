package com.dia.delivery.user.dto;

import com.dia.delivery.user.entity.Users;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;
    public UserResponseDto(Users user) {
        this.username = user.getUsername();
    }
}
