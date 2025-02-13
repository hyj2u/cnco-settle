package com.example.cco.auto;

import com.example.cco.relation.NoConnectionEntity;
import com.example.cco.relation.RelationEntity;
import com.example.cco.relation.StoreRelEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface AutoService {
    public List<AutoTotalEntity> getAutoSettles(String settlementYmd) throws Exception;
    public void uploadKepcoExcel(MultipartFile file) throws Exception;

    public void updateSettlement(String settlementYmd) throws Exception;
}
