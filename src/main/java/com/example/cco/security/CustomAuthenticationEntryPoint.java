package com.example.cco.security;

import com.example.cco.base.ErrorRespDto;
import com.example.cco.base.ErrorWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private  ObjectMapper objectMapper = new ObjectMapper();
    private ErrorWriter errorWriter = new ErrorWriter();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error(errorWriter.getPrintStackTrace(authException));
        // 인증 문제 발생 시 해당 부분 호출
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // 일반 인증 예외 처리
        ErrorRespDto errorRespDto = ErrorRespDto.builder()
                .errorCode("AUTH_002")
                .msg("인증되지 않은 사용자입니다.")
                .build();
        String json = objectMapper.writeValueAsString(errorRespDto);
        response.getWriter().write(json);
    }
}
