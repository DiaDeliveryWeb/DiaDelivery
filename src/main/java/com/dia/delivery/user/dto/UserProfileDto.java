package com.dia.delivery.user.dto;

import com.dia.delivery.user.entity.Users;
import lombok.Getter;

@Getter
public class UserProfileDto {
    private String imageUrl;
    private String email;
    private String introduction;
    private int scrapNum;
    private int reviewNum;

    public UserProfileDto(Users users) {
        this.imageUrl = users.getImageUrl();
        this.email = users.getEmail();
        this.introduction = users.getIntroduction();
        this.scrapNum = users.getUserScrapStoreList().size();
        this.reviewNum = users.getReviewsList().size();
    }
}
