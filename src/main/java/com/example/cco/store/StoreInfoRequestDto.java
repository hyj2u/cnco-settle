package com.example.cco.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreInfoRequestDto {
    private StoreEntity store;
    private List<StoreContractEntity> contracts;
}
