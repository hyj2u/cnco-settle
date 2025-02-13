package com.example.cco.security;

import com.example.cco.member.MemberEntity;
import com.example.cco.member.MemberService;
import com.example.cco.member.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody MemberDto customer) throws Exception {
        log.info("sign up");
        memberService.singUp(customer);
    }

    @GetMapping("/logout/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable String userId) throws Exception {
        memberService.logout(userId);
    }

    @GetMapping("/valid")
    public ResponseEntity validCheck(@AuthenticationPrincipal MemberEntity memberEntity) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "ok");
        data.put("pkey", memberEntity.getPkey());
        data.put("auth", memberEntity.getUserAuthCd());
        return new ResponseEntity(data, HttpStatus.OK);
    }

    @PatchMapping("/pw")
    public ResponseEntity updatePassword(@AuthenticationPrincipal MemberEntity memberEntity, @RequestBody PasswordDto passwordDto) throws Exception {
        memberService.updatePassword(memberEntity, passwordDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
