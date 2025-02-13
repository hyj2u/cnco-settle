package com.example.cco.store;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface StoreSettlementMapping {
    Long getPkey();

    String getStoreName();

    String getStoreCode();


}