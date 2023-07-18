package com.dia.delievery.user.controller;

import com.dia.delievery.common.dto.ApiResponseDto;
import com.dia.delievery.user.dto.SignUpRequestDto;
import com.dia.delievery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/users/signup")
    public ResponseEntity<ApiResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        if(fieldErrors.size()> 0 ){
//            for (FieldError fieldError : fieldErrors){
//                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("회원가입 실패", 400));
//        }

        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원가입 성공", 200));
    }
}