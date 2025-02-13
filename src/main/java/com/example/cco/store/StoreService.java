package com.example.cco.store;

import com.example.cco.file.FileEntity;
import com.example.cco.file.ImgDto;
import com.example.cco.settlement.VsettlementTotalStoreMapping;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoreService {
 public List<StoreListMapping> getStores(String activeYn, String search, String brandCd) throws  Exception;
 public StoreInfoResponseDto getStoreInfo(Long pKey) throws Exception;

 public UrlResource getFiles(String storeCode, String fileType) throws  Exception;
 public void saveStore(StoreInfoRequestDto store) throws  Exception;

 public FileEntity saveStoreFile (String storeCode, String fileType, MultipartFile file) throws Exception;
 public void saveStoreImg (String storeCode,  MultipartFile file) throws Exception;

 public List<ImgDto> getImages(String storeCode) throws Exception;
 public void deleteFile(Long pkey) throws Exception;

 public List<VsettlementTotalStoreMapping> getStoreSettlement(String year, String storeCode) throws Exception;

}
