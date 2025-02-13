package com.example.cco.summary;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VsettlementsTaxMapping {


    String getStoreCode();

    String getStoreName();

    String getOwner();

    Long getSettlementAmount();
    String getBrandName();


}