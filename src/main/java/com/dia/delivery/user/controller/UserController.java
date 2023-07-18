package com.dia.delivery.user.controller;


import com.dia.delivery.common.advice.ApiResponseDto;
import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.*;
import com.dia.delivery.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
//@Controller//-> 앞단 완료되면 바꿀 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody AuthRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("회원가입 실패", 400));
        }

        userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원가입 성공", 200));
    }

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

    //회원정보 변경

    @PutMapping("/inform")
    public ResponseEntity<ApiResponseDto> changeUserInformation(@RequestBody AuthRequestDto requestDto, HttpServletResponse response){

        try {
            userService.change(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResponseDto("회원탈퇴 완료", HttpStatus.CREATED.value()));

    }

    //회원탈퇴


    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseDto> delete(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.delete(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("회원탈퇴 완료", 200));

    }

}











/*   @GetMapping("/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login();
    }

    @PostMapping("/user/signup")
    public SignupResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup();
    }

    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup";
        }

        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    //    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);
        boolean isOwner = (role == UserRoleEnum.OWNER);


        return new UserInfoDto(username, isAdmin, isOwner);
    }

    @DeleteMapping("/userwithdraw")
    public void userWithDraw(){


    }
}*/
