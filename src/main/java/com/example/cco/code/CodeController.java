package com.example.cco.code;

import com.example.cco.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
@Slf4j
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/settle")
    public ResponseEntity getSettlementCdList(@RequestParam Integer brandCd ) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data",codeService.getSettlementCd(brandCd) );
        return new ResponseEntity(map, HttpStatus.OK);
    }



}
