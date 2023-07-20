package com.dia.delivery.user.dto;

import com.dia.delivery.user.UserRoleEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 로 구성되어야 합니다.")
    private String username;

    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9~`!@#$%^&*()-_=+\\\\[{\\\\]}\\\\\\\\|;:'\\\",<.>/?]*$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 로 구성되어야 합니다.")
    private String password;

    private String password2;

    private String password3;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식으로 구성되어야 합니다.")
    private String email;

    private int point;
//    private UserRoleEnum role; // 회원 권한 (ADMIN,OWNER, USER)
    private boolean owner;
    private boolean admin;
    private String ownerToken = "";
    private String adminToken = "";
}
