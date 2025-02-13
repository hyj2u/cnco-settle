package com.example.cco.settlement;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
interface VsettlementsListMapping {

    Long getPkey();

    String getStoreCode();

    String getStoreName();

    Date getSettlementYmd();

    Date getInsertTimestamp();

    String getOwner();

    String getDepositAmount();

    String getSettlementTitle();
    String getClosedYn();

    String getPhone();


}