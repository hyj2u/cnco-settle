package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutoResponsetDto {
    private String storeCode;
    private String storeName;
    private Long taxation;
    private Long taxExemption;
    private Long waveposCash;
    private Long moneyonCard;
    private Long moneyonCardBackup;
    private Long moneyonCashReceipt;
    private Long moneyonCasgReceiptBackup;
    private Long paycoTotalAmount;
    private Long paycoPoint;
    private Long paycoCoupon;
    private Long blueorderTotalSales;
    private Long kepcoElecCharge;

}
