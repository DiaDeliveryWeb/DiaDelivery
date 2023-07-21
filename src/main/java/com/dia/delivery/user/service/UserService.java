package com.dia.delivery.user.service;


import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.image.ImageUploader;
import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.AuthRequestDto;
import com.dia.delivery.user.dto.DeleteRequestDto;
import com.dia.delivery.user.dto.ProfileResponseDto;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploader imageUploader;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String OWNER_TOKEN = "1111";
    private final String ADMIN_TOKEN = "2222";


    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String passwordDecoded = requestDto.getPassword();
        String password = passwordEncoder.encode(requestDto.getPassword()); // 패스워드 평문 암호화
        String password2 = null;
        String password3 = null;
        String email = requestDto.getEmail();
        int point = requestDto.getPoint();

        // 회원 중복 확인
         if(userRepository.findByUsername(username).isPresent()){
             throw new IllegalArgumentException("이미 존재하는 회원 이름입니다.");}

        // 사용자 이메일 중복 확인
         if(userRepository.findByEmail(email).isPresent()){
             throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
         }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isOwner()) {
            if (!OWNER_TOKEN.equals(requestDto.getOwnerToken())) {
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
        Users user = new Users(username, password, passwordDecoded, password2, password3, email, point, role);
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> changeUserInfo(MultipartFile profilePic, String introduction, String password, Users user) throws IOException {
        Users dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow(()->
                new IllegalArgumentException(""));
        if (profilePic != null) {
            String imageUrl = imageUploader.upload(profilePic, "image");
            dbUser.setImageUrl(imageUrl);
        }

        dbUser.setIntroduction(introduction);

        String newPW = password;
        Map<String, String> map = new HashMap<String, String>();
        map.put("pw", user.getPassword());
        map.put("pw2", user.getPassword2());
        map.put("pw3", user.getPassword3());

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!(entry.getValue() == null)) {
                if (passwordEncoder.matches(newPW, entry.getValue())) {
                    throw new IllegalArgumentException("이전 비밀번호와 일치합니다.");
                }
            } else {
                break;
            }
        }

        dbUser.setPassword3(map.get("pw2"));
        dbUser.setPassword2(map.get("pw"));
        dbUser.setPassword(passwordEncoder.encode(newPW));
        dbUser.setPasswordDecoded(newPW);

        ApiResponseDto apiResponseDto = new ApiResponseDto("프로필이 변경되었습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<ApiResponseDto> changeUserInfoPic(MultipartFile profilePic, Users user) throws IOException {
        if (profilePic != null) {
            Users dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow(()->
                    new IllegalArgumentException(""));
            String imageUrl = imageUploader.upload(profilePic, "image");
            dbUser.setImageUrl(imageUrl);
            ApiResponseDto apiResponseDto = new ApiResponseDto("프로필이 변경되었습니다.", HttpStatus.OK.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        }
        ApiResponseDto apiResponseDto = new ApiResponseDto("프로필 사진 제외 변경되었습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    public void delete(DeleteRequestDto requestDto, Users user) {
        if(!requestDto.getPassword().equals(user.getPasswordDecoded())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }
        userRepository.delete(user);
    }
    public boolean isValidString(String input) {
        if (input.length() < 8 || input.length() > 15) {
            return false;
        }
        String regex = "^[a-zA-Z0-9!@#$%^&*()-_=+\\[\\]{}|;:',.<>/?]*$";
        return Pattern.matches(regex, input);
    }

    public ResponseEntity<ProfileResponseDto> getProfile(Users user) {
        Users dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow(()->
                new IllegalArgumentException(""));
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(dbUser.getImageUrl(), dbUser.getIntroduction());
        return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
    }
}



