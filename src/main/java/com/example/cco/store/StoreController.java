package com.example.cco.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity getStoreList(@RequestParam String activeYn,@RequestParam(required = false) String brandCd,
                                       @RequestParam(required = false) String search) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", storeService.getStores(activeYn, search, brandCd));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/{pkey}")
    public ResponseEntity getStoreInfo(@PathVariable Long pkey) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", storeService.getStoreInfo(pkey));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/file/down")
    public ResponseEntity getFiles(@RequestParam String storeCode, @RequestParam String fileType) throws Exception {
        UrlResource resource = storeService.getFiles(storeCode, fileType);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFile().getName(), "utf-8") + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.getFile().length()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
                .body(resource);
    }
    @PostMapping
    public ResponseEntity saveStore(@RequestBody StoreInfoRequestDto store) throws Exception {
        storeService.saveStore(store);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PostMapping("/file/up")
    public ResponseEntity uploadFile(@RequestParam String storeCode, @RequestParam String fileType, @RequestParam MultipartFile file) throws Exception {
        storeService.saveStoreFile(storeCode, fileType, file);
        Map<String, Object> map = new HashMap<>();
        map.put("data", storeService.saveStoreFile(storeCode, fileType, file));
        return new ResponseEntity(map, HttpStatus.CREATED);
    }
    @PostMapping("/img/up")
    public ResponseEntity uploadImg(@RequestParam String storeCode,  @RequestParam MultipartFile file) throws Exception {
        storeService.saveStoreImg(storeCode, file);
        return new ResponseEntity( HttpStatus.CREATED);
    }
    @GetMapping("/img")
    public ResponseEntity getImages(@RequestParam String storeCode) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data",  storeService.getImages(storeCode));
        return new ResponseEntity( map,HttpStatus.OK);
    }

    @DeleteMapping("/file/{pkey}")
    public ResponseEntity deleteFile(@PathVariable Long pkey) throws Exception {
        storeService.deleteFile(pkey);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

    @GetMapping("/settle")
    public ResponseEntity getStoreSettlement(@RequestParam String year, @RequestParam String storeCode) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("data", storeService.getStoreSettlement(year, storeCode));
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
