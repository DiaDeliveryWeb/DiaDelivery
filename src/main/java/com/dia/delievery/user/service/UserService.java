package com.dia.delievery.user.service;

import com.dia.delievery.common.jwt.JwtUtil;
import com.dia.delievery.user.UserRoleEnum;
import com.dia.delievery.user.dto.SignupRequestDto;
import com.dia.delievery.user.entity.Users;
import com.dia.delievery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final String OWNER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<Users> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<Users> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isOwner()) {
            if (!OWNER_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("사장님 권한 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.OWNER;
        }

        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 권한 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        Users user = new Users(username, password, email, role);
        userRepository.save(user);
}}
