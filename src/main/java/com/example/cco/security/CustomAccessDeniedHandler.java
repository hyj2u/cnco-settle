package com.example.cco.security;

import com.example.cco.base.ErrorRespDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // 일반 인증 예외 처리
        ErrorRespDto errorRespDto = ErrorRespDto.builder()
                .errorCode("AUTH_003")
                .msg("권한이 없는 사용자입니다.")
                .build();
        String json = new ObjectMapper().writeValueAsString(errorRespDto);
        response.getWriter().write(json);
    }
}
