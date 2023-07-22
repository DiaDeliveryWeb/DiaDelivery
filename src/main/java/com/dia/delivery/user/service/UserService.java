package com.dia.delivery.user.service;


import com.dia.delivery.common.dto.ApiResponseDto;
import com.dia.delivery.common.image.ImageUploader;
import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.*;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploader imageUploader;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    // OWNER_TOKEN
    private final String OWNER_TOKEN = "1111";


    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String passwordDecoded = requestDto.getPassword();
        String password = passwordEncoder.encode(requestDto.getPassword()); // 패스워드 평문 암호화
        String password2 = null;
        String password3 = null;
        String email = requestDto.getEmail();
    //    int point = requestDto.getPoint();

        // 회원 중복 확인
         if(userRepository.findByUsername(username).isPresent()){
             throw new IllegalArgumentException(
                     messageSource.getMessage(
                             "already.in.username",
                             null,
                             "Already have UserName",
                             Locale.getDefault()
                     )

             );}

        // 사용자 이메일 중복 확인
         if(userRepository.findByEmail(email).isPresent()){
             throw new IllegalArgumentException(
                     messageSource.getMessage(
                     "already.in.email",
                     null,
                     "Already have Email",
                     Locale.getDefault()
             ));
         }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isOwner()) {
            if (!OWNER_TOKEN.equals(requestDto.getOwnerToken())) {
                throw new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.correct.ownertoken",
                                null,
                                "Uncorrect OwnerToken",
                                Locale.getDefault()
                ));
            }
            role = UserRoleEnum.OWNER;
        }


        // 사용자 등록
        Users user = new Users(username, password, passwordDecoded, password2, password3, email, role);
        userRepository.save(user);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> changeUserInfo(MultipartFile profilePic, String introduction, String password, Users user) throws IOException {
        Users dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
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

    public void delete(DeleteRequestDto requestDto, Users user) {
        if (!requestDto.getPassword().equals(user.getPasswordDecoded())) {
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
        Users dbUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new IllegalArgumentException(""));
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(dbUser.getImageUrl(), dbUser.getIntroduction());
        return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
    }

    public String sendMail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String authCode = createCode();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(email); // 메일 수신자
        mimeMessageHelper.setSubject("이메일 인증을 위한 인증 코드 발송"); // 메일 제목
        mimeMessageHelper.setText(authCode); // 인증 코드
        javaMailSender.send(mimeMessage);
        return authCode;
    }

    private String createCode() {    // 이메일 인증번호 생성 메서드
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(4);
            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    @Transactional(readOnly = true)
    public UserInfoDto getUserInfo(Users user) {
        Users users = userRepository.findById(user.getId()).orElseThrow(()-> new IllegalArgumentException("회원정보가 없습니다."));
        String username = user.getUsername();
        boolean isOwner = user.getRole().equals(UserRoleEnum.OWNER);
        return new UserInfoDto(username, isOwner);
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Users user) {
        Users users = userRepository.findById(user.getId()).orElseThrow(()-> new IllegalArgumentException("회원정보가 없습니다."));
        return new UserProfileDto(users);
    }
}



