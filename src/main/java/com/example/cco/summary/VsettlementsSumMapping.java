package com.example.cco.summary;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VsettlementsSumMapping {


    Date getSettlementYmd();
    Integer getStoreCount();

    Long getSettlementAmount();
    Long getDepositAmount();



}