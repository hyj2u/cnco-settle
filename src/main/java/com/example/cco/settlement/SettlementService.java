package com.example.cco.settlement;

import com.example.cco.member.MemberEntity;
import com.example.cco.store.StoreSettlementMapping;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SettlementService {
    public List<SettlementBaseFieldEntity> getSettlementBaseFieldEntities(String defaultYn) throws Exception;

    public List<VsettlementsForWriteMapping> getSettlementsForWrite(String settlementYmd, String search) throws Exception;

    public void updateSettlementsList(List<SettlementsUpdateRequestDto> settlementsUpdateRequestDtos) throws Exception;

    public void downloadSettlementsList(String settlementYmd, HttpServletResponse httpServletResponse) throws Exception;

    public void uploadSettlementsList(String settlementYmd, MultipartFile file) throws Exception;

    public SettlementListDto getSettlementsList(String settlementYmd, String search, int page, String activeYn) throws Exception;
    public SettlementListDto getSettlementsListForFranchise(String settlementYmd, String search, int page, MemberEntity member) throws Exception;

    public SettlementDetailDto getSettlementDetail(Long pkey) throws Exception;
    public SettlementDetailDto getSecretOrderSettlment(String code, String settlementYmd) throws Exception;
    public void updateSettlementDetail(SettlementDetailDto detailDto) throws Exception;

    public List<StoreSettlementMapping> getSettlementStores(String settlementYmd) throws Exception;

    public void finishSettlement(String settlementYmd) throws Exception;
    public void changeCloseStatus(Long pkey, String closedYn) throws Exception;

}
