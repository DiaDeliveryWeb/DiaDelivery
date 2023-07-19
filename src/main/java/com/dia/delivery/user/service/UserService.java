package com.dia.delivery.user.service;


import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.common.security.UserDetailsImpl;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.AuthRequestDto;
import com.dia.delivery.user.dto.LoginRequestDto;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final String OWNER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String password2 = null;
        String password3 = null;

        // 사용자 이름 중복 확인
         if(userRepository.findByUsername(username).isPresent()){
             throw new IllegalArgumentException("이미 존재하는 회원 이름입니다.");}

        // 사용자 이메일 중복 확인
        String email = requestDto.getEmail();
         if(userRepository.findByEmail(email).isPresent()){
             throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");}

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
        Users user = new Users(username, password, password2, password3, email, role);
        userRepository.save(user);}



    public void login(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 확인 (username 이 없는 경우)
        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //비밀번호 확인 (password 가 다른 경우)
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

             public void delete(Long id) {
            //     Users users = userRepository.findById(id).orElseThrow(
              //           () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
               //  );
        userRepository.deleteById(id);
        }


    //사용자 정보 변경
  public void changeUserPassword(Long id, LoginRequestDto requestDto) {

      String newPW = requestDto.getPassword();
      Users user = userRepository.findById(id).orElseThrow(
              () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
      );

      Map<String,String> map = new HashMap<String,String>();
      map.put("pw",user.getPassword());
      map.put("pw2",user.getPassword2());
      map.put("pw3",user.getPassword3());

      for(Map.Entry<String, String> entry : map.entrySet()){
          if(!(entry.getValue() == null)) {
              if (passwordEncoder.matches(newPW, entry.getValue())) {
                  throw new IllegalArgumentException("비밀번호가 일치하면 변경이 안됩니다.");
              }
          }else{
              break;
          }
      }

      user.setPassword3(map.get("pw2"));
      String test = map.get("pw");
      user.setPassword2(map.get("pw"));
      user.setPassword(passwordEncoder.encode(newPW));

        userRepository.save(user);
    }

}



