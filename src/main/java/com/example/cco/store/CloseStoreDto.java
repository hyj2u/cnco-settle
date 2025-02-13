package com.example.cco.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloseStoreDto {
    private Long pkey;
    private String closingYmd;
    private String closingDesc;


}
