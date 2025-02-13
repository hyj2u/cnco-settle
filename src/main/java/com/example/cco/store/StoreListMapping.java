package com.example.cco.store;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface StoreListMapping {
    Long getPkey();

    String getStoreName();

    String getOwner();

    String getManager();

    String getStoreCode();

    String getActiveYn();

    Date getClosingYmd();

    String getClosingDesc();

    Date getContractEndYmd();

    String getPhone();

    String getStoreAddress();

    String getBrandCd();
    Long getPrepaidRent();
    Integer getPrepaidMonth();

}