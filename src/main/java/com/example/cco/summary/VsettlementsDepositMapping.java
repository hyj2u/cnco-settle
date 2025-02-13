package com.example.cco.summary;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VsettlementsDepositMapping {

    String getBrandName();

    String getStoreCode();

    String getStoreName();


    String getOwner();

    Long getDepositAmount();

    String getBank();

    String getBankAccountNumber();

    String getBankAccount();


}