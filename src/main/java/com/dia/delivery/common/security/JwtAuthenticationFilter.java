package com.dia.delivery.common.security;


import com.dia.delivery.common.advice.ApiResponseDto;
import com.dia.delivery.common.jwt.JwtUtil;
import com.dia.delivery.user.UserRoleEnum;
import com.dia.delivery.user.dto.AuthRequestDto;
import com.dia.delivery.user.entity.Users;
import com.dia.delivery.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

import static com.dia.delivery.user.UserBlockEnum.차단;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            AuthRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), AuthRequestDto.class);
            Users user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(()-> new IllegalArgumentException("유저가 없습니다."));
            if (user.getStatus().equals(차단)) {
                ApiResponseDto apiResponseDto = new ApiResponseDto("차단된 회원입니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String json = new ObjectMapper().writeValueAsString(apiResponseDto);
                response.getWriter().write(json);
                return null;
            }
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인성공");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
//        token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
//        Cookie cookie = new Cookie(jwtUtil.AUTHORIZATION_HEADER, token);
//        cookie.setMaxAge(3600); // 쿠키 유효 기간 설정 (여기서는 1시간)
//        cookie.setPath("/"); // 쿠키의 유효 범위 설정 (루트 경로에 모든 요청에 대해 쿠키 전송)
        // 쿠키를 응답 헤더에 추가
//        response.addCookie(cookie);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setMsg("로그인 성공");
        apiResponseDto.setStatusCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new ObjectMapper().writeValueAsString(apiResponseDto);
        response.getWriter().write(json);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}