package com.example.cco.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreInfoResponseDto {
    private StoreEntity store;

    private String businessNumFileName;
    private Long businessNumFileId;
    private String openFinFileName;
    private Long openFileFileId;
    private List<StoreContractEntity> contracts;



}
