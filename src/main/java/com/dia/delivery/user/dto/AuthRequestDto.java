package com.dia.delivery.user.dto;

import com.dia.delivery.user.UserRoleEnum;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 로 구성되어야 합니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{8,15}$",
            message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 로 구성되어야 합니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{8,15}$",
            message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 로 구성되어야 합니다.")
    private String password2;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{8,15}$",
            message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 로 구성되어야 합니다.")
    private String password3;

    @Pattern(regexp = "(randomString)@(randomString2).(2-3 characters)",
            message = "이메일 형식으로 구성되어야 합니다.")
    private String email;

    private Integer point;

    private UserRoleEnum role; // 회원 권한 (ADMIN,OWNER, USER)

    private boolean owner = false;
    private boolean admin = false;

    private String adminToken = "";
    private String ownerToken = "";
}
