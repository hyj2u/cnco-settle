package com.example.cco.settlement;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
interface VsettlementsForWriteMapping {

    Long getPkey();

    String getStoreCode();

    String getStoreName();

    Date getSettlementYmd();

    Integer getA1cash();

    Integer getA1card();
    Integer getA1tax();

    Integer getB1rent();

    Integer getB1maint();

    Integer getB1electricity();

    Integer getB1gas();

    Integer getB1internet();
    Integer getB1etcExpense();

    Integer getB2charge();

    Integer getB3material();

    Integer getB3etc();

    Integer getB4initProduct();

    Integer getB4donation();

    Integer getC1cardFee();

    Integer getC1rent();

    Integer getC1electricity();

    Integer getC1water();

    Integer getC1gas();

    Integer getC1insurance();

    Integer getC1etcExpense();

    Integer getC2interestCost();


    Integer getD1storeMaint();

    Integer getD1material();

    Integer getD1carryover();

    Integer getD2cash();

    Integer getD3rent();

    Integer getD3initProduct();



}