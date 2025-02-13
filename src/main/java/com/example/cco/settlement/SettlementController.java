package com.example.cco.settlement;

import com.example.cco.member.MemberEntity;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/settle")
@RequiredArgsConstructor
@Slf4j
public class SettlementController {
    private final SettlementService settlementService;

    @GetMapping("/field")
    public ResponseEntity getSettlemnetFields(@RequestParam(required = false) String defaultYn) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", settlementService.getSettlementBaseFieldEntities(defaultYn));
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @GetMapping("/write")
    public ResponseEntity getSettlemnetsForWrite(@RequestParam String settlementYmd, @RequestParam(required = false) String search) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", settlementService.getSettlementsForWrite(settlementYmd, search));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity updateSettlemnets(@RequestBody List<SettlementsUpdateRequestDto> settlementsUpdateRequestDtos) throws Exception {

        settlementService.updateSettlementsList(settlementsUpdateRequestDtos);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/down")
    public void getSettlemnetsDownload(@RequestParam String settlementYmd, HttpServletResponse response) throws Exception {
        settlementService.downloadSettlementsList(settlementYmd, response);
    }

    @PostMapping("/up")
    public ResponseEntity settlemnetsUpload(@RequestParam MultipartFile file, @RequestParam String settlementYmd) throws Exception {
        settlementService.uploadSettlementsList(settlementYmd, file);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getSettlemnetsList(@RequestParam String settlementYmd, @RequestParam(required = false) String search,
                                             @RequestParam (required = false, defaultValue = "1") int page, String activeYn) throws Exception {
        Map<String, Object> map = new HashMap<>();
        log.info("search=" + search);
        map.put("data", settlementService.getSettlementsList(settlementYmd, search, page, activeYn));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/fran")
    public ResponseEntity getSettlemnetsListForFranchise(@RequestParam String settlementYmd, @RequestParam(required = false) String search,
                                                         @RequestParam (required = false, defaultValue = "1") int page, @AuthenticationPrincipal MemberEntity
                                                         member) throws Exception {
        Map<String, Object> map = new HashMap<>();
        log.info("search=" + search);
        map.put("data", settlementService.getSettlementsListForFranchise(settlementYmd, search, page, member));
        return new ResponseEntity(map, HttpStatus.OK);
    }


    @GetMapping("/{pkey}")
    public ResponseEntity getSettlementDetail(@PathVariable Long pkey) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", settlementService.getSettlementDetail(pkey));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/secret")
    public ResponseEntity getSecretOrderSettlementDetail(@RequestParam String code, @RequestParam String settlementYmd) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", settlementService.getSecretOrderSettlment(code, settlementYmd));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PatchMapping("/{pkey}")
    public ResponseEntity settlemnetsUpdate(@RequestBody SettlementDetailDto settlementDetailDto) throws Exception {
        settlementService.updateSettlementDetail(settlementDetailDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity settlemnetsInsert(@RequestBody SettlementDetailDto settlementDetailDto) throws Exception {
        settlementService.updateSettlementDetail(settlementDetailDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/store")
    public ResponseEntity getSettlementStores(@RequestParam String settlementYmd) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", settlementService.getSettlementStores(settlementYmd));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PatchMapping("/fin")
    public ResponseEntity finishSettlement(@RequestParam String settlementYmd) throws Exception {
        settlementService.finishSettlement(settlementYmd);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/fin/{pkey}")
    public ResponseEntity changeCloseStatus(@PathVariable Long pkey ,@RequestParam String closedYn) throws Exception {
        settlementService.changeCloseStatus(pkey, closedYn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
