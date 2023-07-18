package com.dia.delivery.user.controller;

import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.user.dto.EmailAuthRequestDto;
import com.dia.delivery.user.dto.SignUpRequestDto;
import com.dia.delivery.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size()> 0 ){
            for (FieldError fieldError : fieldErrors){
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("회원가입 실패", 400));
        }

        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원가입 성공", 200));
    }

    @PostMapping("/user/email-auth")
    public String sendMail(@RequestBody EmailAuthRequestDto requestDto) throws MessagingException {
        return  userService.sendMail(requestDto.getEmail());
    }

    @DeleteMapping("/user/withdraw")
    public ResponseEntity<ApiResponseDto> withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.delete(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원탈퇴 완료", 200));
    }
}