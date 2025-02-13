package com.example.cco.file;

import com.example.cco.member.MemberDto;
import com.example.cco.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
@Slf4j
public class ImgController {

    private final ImgService imgService;

    @GetMapping("/{pkey}")
    public ResponseEntity getImage(@PathVariable Long pkey) throws Exception {
        return new ResponseEntity(imgService.getImage(pkey), HttpStatus.OK);
    }



}
