package com.example.cco.auto;

import com.example.cco.relation.RelationService;
import com.example.cco.relation.StoreRelEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auto")
@RequiredArgsConstructor
@Slf4j
public class AutoController {
    private final AutoService autoService;

    @GetMapping
    public ResponseEntity getAutoSettle(@RequestParam String settlementYmd) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", autoService.getAutoSettles(settlementYmd));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @PostMapping("/kepco")
    public ResponseEntity uploadKepcoExcel(@RequestParam MultipartFile file) throws Exception {

        autoService.uploadKepcoExcel(file);
        return new ResponseEntity( HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity updateSettlement(@RequestParam String settlementYmd) throws Exception {
        autoService.updateSettlement(settlementYmd);
        return new ResponseEntity( HttpStatus.CREATED);
    }



}
