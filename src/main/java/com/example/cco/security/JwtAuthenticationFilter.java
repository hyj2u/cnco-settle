package com.example.cco.security;

import com.example.cco.base.ErrorRespDto;
import com.example.cco.member.MemberEntity;
import com.example.cco.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final RefreshTknRepository refreshTknRepository;
    private final PasswordEncoder passwordEncoder;


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, MemberRepository memberRepository,
                                   RefreshTknRepository refreshTknRepository, PasswordEncoder passwordEncoder
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.refreshTknRepository = refreshTknRepository;
        this.passwordEncoder = passwordEncoder;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        MemberEntity member = null;
        try {
            member = objectMapper.readValue(request.getInputStream(), MemberEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getPassword());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MemberEntity member = (MemberEntity) authResult.getPrincipal();

        // 저장
        MemberEntity findMember = memberRepository.findByUserId(member.getUserId()).get();
        TokenDto tokenDto = null;
        logger.info(passwordEncoder.encode("Blue1234!!"));
        if (passwordEncoder.matches("Blue1234!!", member.getPassword())) {
            tokenDto = jwtTokenProvider.createToken(member.getUserId(), true);
        } else {
            tokenDto = jwtTokenProvider.createToken(member.getUserId(), false);
        }


        refreshTknRepository.deleteByMember(findMember);

        RefreshTknEntity refreshTkn = new RefreshTknEntity();
        refreshTkn.setRefreshTkn(refreshTkn.getRefreshTkn());
        refreshTkn.setMember(findMember);
        refreshTknRepository.save(refreshTkn);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokenDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorRespDto errorRespDto = ErrorRespDto.builder()
                .errorCode("AUTH_001")
                .msg("아이디 비밀번호가 일치하지않습니다.")
                .build();
        String json = new ObjectMapper().writeValueAsString(errorRespDto);
        response.getWriter().write(json);
        //super.unsuccessfulAuthentication(request, response, failed);
    }
}
