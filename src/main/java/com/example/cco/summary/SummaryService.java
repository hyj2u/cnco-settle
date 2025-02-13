package com.example.cco.summary;

import com.example.cco.settlement.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface SummaryService {

    public List<VsettlementsDepositMapping> getDepositList(String settlementYmd) throws Exception;
    public void downDepositList(String settlementYmd, HttpServletResponse response) throws Exception;
    public void downTaxList(String settlementYmd, HttpServletResponse response) throws Exception;
    public List<VsettlementsTaxMapping> getTaxList(String settlementYmd) throws Exception;
    public VsettlementSummaryEntity getMonSettlement(String settlementYmd) throws Exception;
    public List<VsettlementsSumMapping> getSummaryTotal(String year, String activeYn) throws Exception;
}
