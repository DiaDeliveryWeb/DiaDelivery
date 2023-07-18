package com.dia.delievery.user.service;

import com.dia.delievery.common.image.ImageUploader;
import com.dia.delievery.user.UserRoleEnum;
import com.dia.delievery.user.dto.SignUpRequestDto;
import com.dia.delievery.user.entity.Users;
import com.dia.delievery.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final ImageUploader imageUploader;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public void signup(SignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        UserRoleEnum role = requestDto.getRole();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저");
        }

        Users user = new Users(username, password, email, role); // 이메일 추가
        userRepository.save(user);
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

    private String createCode() {
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

    public void delete(Users user) {
        userRepository.delete(user);
    }
}
