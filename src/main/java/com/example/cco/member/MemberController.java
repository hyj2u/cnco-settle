package com.example.cco.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity getMembers(@RequestParam(required = false) String search) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", memberService.getMembers(search));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody MemberDto memberDto) throws Exception {
        log.info("sign up");
        memberService.singUp(memberDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity modifyMember(@RequestBody MemberDto memberDto) throws Exception {
        memberService.updateMember(memberDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @GetMapping("/{pkey}")
    public ResponseEntity getMember(@PathVariable Long pkey) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", memberService.getMember(pkey));
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
