package com.dia.delivery.user.controller;

import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.*;
import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/users/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody AuthRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            throw new IllegalArgumentException("회원가입 아이디 비밀번호 입력 양식을 맞춰주세요.");
        }
        userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원가입 성공", HttpStatus.OK.value()));
    }

    /*@PostMapping("/user/email-auth")
    public ResponseEntity<ApiResponseDto> sendMail(@RequestBody EmailAuthRequestDto requestDto) throws MessagingException {
         userService.sendMail(requestDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("이메일보내기 성공", 200));
    }*/


    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);
        boolean isOwner = (role == UserRoleEnum.OWNER);
        return new UserInfoDto(username, isAdmin, isOwner);
    }

    // 프로필 수정
    @PutMapping(value = "/users/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseDto> changeUserInfo(@RequestPart("profilePic") MultipartFile profilePic,
                                                         @RequestPart("introduction") String introduction,
                                                         @RequestPart("password") String password, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        if (!userService.isValidString(password)) {
            throw new IllegalArgumentException("비밀번호 양식을 확인해주세요.");
        }
        userService.changeUserInfo(profilePic, introduction, password, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("회원수정 완료", HttpStatus.CREATED.value()));

    }

    // 회원탈퇴
    @DeleteMapping("/users/withdrawal")
    public ResponseEntity<ApiResponseDto> delete(@RequestBody DeleteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.delete(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원탈퇴 완료", 200));

    }

    @GetMapping("/users/info")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.getUser());
    }
}


