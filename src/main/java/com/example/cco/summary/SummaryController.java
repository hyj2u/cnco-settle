package com.example.cco.summary;

import com.example.cco.settlement.SettlementDetailDto;
import com.example.cco.settlement.SettlementService;
import com.example.cco.settlement.SettlementsUpdateRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sum")
@RequiredArgsConstructor
@Slf4j
public class SummaryController {
    private final SummaryService summaryService;

    @GetMapping("/deposit")
    public ResponseEntity getDepositList(@RequestParam String settlementYmd) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", summaryService.getDepositList(settlementYmd));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/deposit/excel")
    @ResponseStatus(HttpStatus.OK)
    public void getDepositList(@RequestParam String settlementYmd, HttpServletResponse response) throws Exception {
      summaryService.downDepositList(settlementYmd, response);
    }
    @GetMapping("/tax")
    public ResponseEntity getTaxList(@RequestParam String settlementYmd) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", summaryService.getTaxList(settlementYmd));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/tax/excel")
    @ResponseStatus(HttpStatus.OK)
    public void getTaxList(@RequestParam String settlementYmd, HttpServletResponse response) throws Exception {
        summaryService.downTaxList(settlementYmd, response);
    }
    @GetMapping
    public ResponseEntity getMonSettlement(@RequestParam String settlementYmd) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", summaryService.getMonSettlement(settlementYmd));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/total")
    public ResponseEntity getSummaryTotal(@RequestParam String year, @RequestParam String activeYn) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", summaryService.getSummaryTotal(year, activeYn));
        return new ResponseEntity(map, HttpStatus.OK);
    }



}
