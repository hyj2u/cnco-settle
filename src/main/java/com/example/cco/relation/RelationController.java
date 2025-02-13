package com.example.cco.relation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relation")
@RequiredArgsConstructor
@Slf4j
public class RelationController {
    private final RelationService relationService;

    @GetMapping
    public ResponseEntity getRelationsNeeded(@RequestParam (required = false, defaultValue = "1") int page)  throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", relationService.getRelationsNeeded(page));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @GetMapping("/{storeCode}")
    public ResponseEntity getStoreRelation(@PathVariable String storeCode) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", relationService.getStoreRelation(storeCode));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/excel")
    @ResponseStatus(HttpStatus.OK)
    public void getRelationsExcel(HttpServletResponse response) throws Exception {
        relationService.excelDownRelations(response);
    }

    @GetMapping("/no")
    public ResponseEntity getNoConnected(@RequestParam String gb) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", relationService.getNoConnections(gb));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity makeRelation(@RequestBody List<StoreRelEntity> storeRelEntity) throws Exception {
        relationService.insertRelation(storeRelEntity);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PostMapping("/list")
    public ResponseEntity makeRelationList(@RequestBody List<StoreRelEntity> storeRelEntity) throws Exception {
        relationService.insertRelationList(storeRelEntity);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/terminal")
    public ResponseEntity getStoreTerminal(@RequestParam String storeCode) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", relationService.getMoneyonTerminal(storeCode));
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @PostMapping("/terminal")
    public ResponseEntity setStoreTerminal(@RequestBody StoreTerminalDto storeTerminalDto) throws Exception {
        relationService.setMoneyonTerminal(storeTerminalDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }



}
