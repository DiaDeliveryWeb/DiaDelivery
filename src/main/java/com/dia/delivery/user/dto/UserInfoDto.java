package com.dia.delivery.user.dto;

import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserInfoDto {
    private String username;
    //  boolean isAdmin;
    private boolean isOwner;

    public UserInfoDto(String username, boolean isOwner) {
        this.username = username;
        this.isOwner = isOwner;
    }
}
