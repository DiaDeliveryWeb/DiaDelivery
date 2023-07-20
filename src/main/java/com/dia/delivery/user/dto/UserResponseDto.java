package com.dia.delivery.user.dto;

import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.entity.Users;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;
    private UserRoleEnum role;
    private int scrapNum;
    private int reviewNum;
    public UserResponseDto(Users user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.reviewNum = user.getReviewsList().size();
        this.scrapNum = user.getUserScrapStoreList().size();
    }
}
