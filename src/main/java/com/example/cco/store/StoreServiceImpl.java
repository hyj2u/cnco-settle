package com.example.cco.store;

import com.example.cco.base.DateConverter;
import com.example.cco.base.FileUtil;
import com.example.cco.file.FileEntity;
import com.example.cco.file.FileRepository;
import com.example.cco.file.ImgDto;
import com.example.cco.member.MemberEntity;
import com.example.cco.member.MemberRepository;
import com.example.cco.relation.StoreRelEntity;
import com.example.cco.relation.StoreRelRepository;
import com.example.cco.settlement.VsettlementTotalRepository;
import com.example.cco.settlement.VsettlementTotalStoreMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final FileRepository fileRepository;
    private final FileUtil fileUtil;

    private final VsettlementTotalRepository vsettlementTotalRepository;
    private final DateConverter dateConverter;
    private final  StoreContactRepository contactRepository;
    private final StoreRelRepository storeRelRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${img.url}")
    private  String imgUrl;

    private final ObjectMapper objectMapper;
    @Override
    public List<StoreListMapping> getStores(String activeYn, String search, String brandCd) throws Exception {
        log.info("brand="+ brandCd+" / search = "+ search);
        search = "%"+search+"%";
        if (brandCd == null || brandCd.equals("") ){
            if(search ==null || search.equals("")){
                return  storeRepository.findAllByActiveYn(activeYn);
            }else{
                return  storeRepository.findAllByActiveYnAndStoreNameContainingOrOwnerContainingOrManagerContaining(activeYn, search, search, search);
            }
        }else{
            if(search ==null || search.equals("")){
                return  storeRepository.findAllByActiveYnAndBrandCd(activeYn, brandCd);
            }else{
                return  storeRepository.findAllByActiveYnAndBrandCdAndStoreNameContainingOrOwnerContainingOrManagerContaining(activeYn, brandCd, search, search, search);
            }
        }

    }

    @Override
    public StoreInfoResponseDto getStoreInfo(Long pKey) throws Exception {
        StoreEntity store = storeRepository.findById(pKey).get();
        StoreInfoResponseDto storeInfoResponseDto = new StoreInfoResponseDto();
        storeInfoResponseDto.setStore(store);
        storeInfoResponseDto.setContracts(contactRepository.findAllByStoreCodeOrderByContractStartYmdDesc(store.getStoreCode()));
        if(fileRepository.findByStoreCodeAndFileType(store.getStoreCode(), "business_num").isPresent()){
            FileEntity file = fileRepository.findByStoreCodeAndFileType(store.getStoreCode(), "business_num").get();
            storeInfoResponseDto.setBusinessNumFileName(file.getFileName());
            storeInfoResponseDto.setBusinessNumFileId(file.getPkey());
        }
        if(fileRepository.findByStoreCodeAndFileType(store.getStoreCode(), "open_fin").isPresent()){
            FileEntity file = fileRepository.findByStoreCodeAndFileType(store.getStoreCode(), "open_fin").get();
            storeInfoResponseDto.setOpenFinFileName(file.getFileName());
            storeInfoResponseDto.setOpenFileFileId(file.getPkey());
        }

        return storeInfoResponseDto;
    }

    @Override
    public UrlResource getFiles(String storeCode, String fileType) throws Exception {
      FileEntity file = fileRepository.findByStoreCodeAndFileType(storeCode, fileType).get();
      UrlResource resource = new UrlResource("file:" +file.getFilePath());
      return resource;
    }

    @Override
    public List<ImgDto> getImages(String storeCode) throws Exception {
        List<ImgDto> imgDtos = new ArrayList<>();
        for(FileEntity fileEntity : fileRepository.findAllByStoreCodeAndFileType(storeCode, "store_img")){
            ImgDto imgDto = new ImgDto();
            imgDto.setPkey(fileEntity.getPkey());
            imgDto.setSrc(imgUrl +fileEntity.getPkey());
            imgDtos.add(imgDto);
        }
        return imgDtos;
    }

    @Override
    public void deleteFile(Long pkey) throws Exception {
        fileRepository.deleteById(pkey);
    }

    @Override
    public void saveStore(StoreInfoRequestDto store) throws Exception {
        if(store.getStore().getActiveYn().equals("N")){
            List<StoreRelEntity> storeRelEntities =storeRelRepository.findAllByStoreCodeAndEndTimestampIsNull(store.getStore().getStoreCode());
            if(!storeRelEntities.isEmpty()){
                for(StoreRelEntity storeRel : storeRelEntities){
                    storeRel.setEndTimestamp(LocalDateTime.now());
                    storeRelRepository.save(storeRel);
                }
            }

        }
        storeRepository.save(store.getStore());

        contactRepository.deleteAllByStoreCode(store.getStore().getStoreCode());
        if(store.getContracts()!=null) {
            for (StoreContractEntity contractEntity : store.getContracts()) {
                contractEntity.setPkey(null);
                contractEntity.setStoreCode(store.getStore().getStoreCode());
                contactRepository.save(contractEntity);
            }
        }
        String phone = store.getStore().getPhone();
        //점주용 계정생성 로직 추가
        if(phone!= null && !memberRepository.findByUserId(phone).isPresent()){
            MemberEntity member = new MemberEntity();
            member.setUserId(phone);
            member.setUserPw(passwordEncoder.encode("Blue1234!!"));
            member.setUserName(phone);
            member.setUserAuthCd("ROLE_FRANCHISE");
            member.setActiveYn("Y");
            member.setPhone(phone);
            memberRepository.save(member);
        }

    }

    @Override
    public FileEntity saveStoreFile(String storeCode, String fileType, MultipartFile file) throws Exception {
        FileEntity fileEntity = null;
        if(fileRepository.findByStoreCodeAndFileType(storeCode, fileType).isPresent()){
            fileEntity = fileRepository.findByStoreCodeAndFileType(storeCode, fileType).get();
        }else{
            fileEntity = new FileEntity();
            fileEntity.setStoreCode(storeCode);
            fileEntity.setFileType(fileType);
        }
        fileEntity.setFilePath(fileUtil.createFile(file));
        fileEntity.setFileName(file.getOriginalFilename());
        fileRepository.save(fileEntity);
        log.info(objectMapper.writeValueAsString(fileEntity));
        return  fileEntity;
    }

    @Override
    public void saveStoreImg(String storeCode, MultipartFile file) throws Exception {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setStoreCode(storeCode);
        fileEntity.setFileType("store_img");
        fileEntity.setFilePath(fileUtil.createFile(file));
        fileEntity.setFileName(file.getOriginalFilename());
        fileRepository.save(fileEntity);
    }

    @Override
    public List<VsettlementTotalStoreMapping> getStoreSettlement(String year, String storeCode) throws Exception {
        return vsettlementTotalRepository.findAllByStoreCodeAndSettlementYmdBetweenOrderBySettlementYmd(storeCode, dateConverter.stringToYearStart(year),
                dateConverter.stringToYearEnd(year));
    }
}
