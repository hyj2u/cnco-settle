package com.example.cco.base;

import com.example.cco.member.MemberEntity;
import com.example.cco.member.MemberRepository;
import com.example.cco.member.MemberService;
import com.example.cco.member.MemberDto;
import com.example.cco.security.PasswordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Slf4j
public class HealthController {

    private final MemberRepository memberRepository;



    @GetMapping
    public ResponseEntity healthCheck() throws Exception {
        memberRepository.findByUserId("admin");
        return new ResponseEntity(HttpStatus.OK);
    }




}
