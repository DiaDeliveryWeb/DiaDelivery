package com.dia.delivery.user.service;

import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.SignUpRequestDto;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public void signup(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = requestDto.getRole();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저");
        }

        Users user = new Users(username, password, role); // 이메일 추가
        userRepository.save(user);
    }

}
