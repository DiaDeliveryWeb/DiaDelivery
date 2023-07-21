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
    private int scrapNum;
    private int reviewNum;

    public UserInfoDto(Users user) {
        this.username = user.getUsername();
        this.isOwner = (user.getRole().equals(UserRoleEnum.OWNER));
        System.out.println(isOwner());
        this.reviewNum = user.getReviewsList().size();
        this.scrapNum = user.getUserScrapStoreList().size();
    }
}
