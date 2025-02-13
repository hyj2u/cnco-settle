package com.example.cco.security;

import com.example.cco.base.ErrorRespDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. 헤더 요청 정보에서 토큰 가져오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        log.info("token =" + token);
        // 2. 유효기간 검증
        if (token != null) {
            if (jwtTokenProvider.validateToken(token)) {
                // 3. token 내부의 username을 통해 Authentication 생성
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // 4. Authentication을 SecurityContextHolder에 세팅.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.info("토큰만료");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorRespDto errorRespDto = ErrorRespDto.builder()
                        .errorCode("AUTH_004")
                        .msg("토큰이 만료되었습니다.")
                        .build();
                String json = new ObjectMapper().writeValueAsString(errorRespDto);
                response.getWriter().write(json);
            }
        }
        chain.doFilter(request, response);
    }
}
