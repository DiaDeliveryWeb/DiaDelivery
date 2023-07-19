package com.dia.delivery.user.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.user.dto.SignUpRequestDto;
import com.dia.delivery.user.dto.UserResponseDto;
import com.dia.delivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/users/signup")
    public ResponseEntity<ApiResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원가입 성공", 200));
    }

    @GetMapping("/users/user-info")
    public UserResponseDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new UserResponseDto(userDetails.getUser());
    }
}